package br.erickweil.grafos;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.NoSuchElementException;

public class GrafoPixels extends Grafo<GrafoPixels.RGBPixel> {
    BufferedImage image;
    int width, height;
    public GrafoPixels(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public static class RGBPixel {
        private int color; // Armazena a cor no formato 0xRRGGBB
    
        public RGBPixel(int red, int green, int blue) {
            setColor(red, green, blue);
        }
    
        public RGBPixel(int color) {
            this.color = color & 0xFFFFFF; // Garante que apenas RRGGBB seja armazenado
        }

        private int clamp(int value) {
            return Math.max(0, Math.min(255, value)); // Garante que fique entre 0 e 255
        }
    
        public void setColor(int red, int green, int blue) {
            red = clamp(red);
            green = clamp(green);
            blue = clamp(blue);
            this.color = (red << 16) | (green << 8) | blue;
        }
    
        public int getColor() {
            return color;
        }
    
        public int getRed() {
            return (color >> 16) & 0xFF;
        }
    
        public int getGreen() {
            return (color >> 8) & 0xFF;
        }
    
        public int getBlue() {
            return color & 0xFF;
        }
    }

    public static BufferedImage gerarImagem(int w, int h) {
        var image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        // Preenchendo a imagem com pixels pretos e brancos aleatórios
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                // aleatoriamente será preto ou branco
                boolean isBlack = Math.random() > 0.5;
                int rgb = isBlack ? 0x000000 : 0xFFFFFF;
                image.setRGB(x, y, rgb);
            }
        }
        
        return image;
    }

    public static class PixelVertice extends GrafoVertice<RGBPixel> {
        GrafoPixels grafo;
        int x, y;

        public PixelVertice(GrafoPixels grafo, int x, int y) {
            this.grafo = grafo;
            this.x = x;
            this.y = y;
        }

        // Vizinho na posição (vx, vy), ou null se estiver fora da imagem ou for preto (parede)
        private GrafoVertice<RGBPixel> vizinho(int vx, int vy) {
            if (vx < 0 || vx >= grafo.width || vy < 0 || vy >= grafo.height) return null;
            var v = grafo.getVertice(vy * grafo.width + vx);
            if (v.getValor().getBlue() == 0) return null;
            return v;
        }

        @Override
        public Iterator<GrafoVertice<RGBPixel>> iterator() {
            // Atravessa os vizinhos da esquerda, direita, cima e baixo
            return Stream.of(vizinho(x-1, y), vizinho(x+1, y), vizinho(x, y-1), vizinho(x, y+1))
                .filter(Objects::nonNull)
                .iterator();
        }

        // Cada getVertice cria um novo objeto, então dois vértices são iguais se apontam para o mesmo pixel
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof PixelVertice other)) return false;
            return grafo == other.grafo && x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return y * grafo.width + x;
        }
        @Override
        public RGBPixel getValor() {
            return new RGBPixel(grafo.image.getRGB(x, y));
        }

        @Override
        public int getEstado() {
            return getValor().getRed();
        }

        @Override
        public void setEstado(int estado) {
            RGBPixel color = getValor();
            grafo.image.setRGB(x, y, new RGBPixel(estado, color.getGreen(), color.getBlue()).color);
        }

        @Override
        public void addConexao(GrafoVertice<RGBPixel> v) {
            // ?
        }
    }

    @Override
    public Iterator<GrafoVertice<RGBPixel>> iterator() {
        // Atravessa todos os pixels da imagem
        return new Iterator<GrafoVertice<RGBPixel>>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < width * height;
            }

            @Override
            public GrafoVertice<RGBPixel> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return getVertice(index++);
            }
        };
    }

    @Override
    public int criarVertice(RGBPixel valor) {
        return -1;
    }

    @Override
    public void conectar(int a, int b) {
        return;
    }

    @Override
    public void conectarDirecionado(int a, int b) {
        return;
    }

    @Override
    public GrafoVertice<RGBPixel> getVertice(int v) {
        int y = v / width;
        int x = v % width;

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }

        return new PixelVertice(this, x, y);
    }

    public static void main(String[] args) {
        GrafoPixels grafo = new GrafoPixels(gerarImagem(200, 200));


    }
}
