package br.erickweil.grafos;
import java.awt.image.BufferedImage;
import java.util.Iterator;
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

        // Preenchendo a imagem com um padrão (exemplo: degradê azul)
        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 200; x++) {
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

        @Override
        public Iterator<GrafoVertice<RGBPixel>> iterator() {            
            var v0 = grafo.getVertice(y * grafo.width + (x-1));
            var v1 = grafo.getVertice(y * grafo.width + (x+1));
            var v2 = grafo.getVertice((y-1) * grafo.width + x);
            var v3 = grafo.getVertice((y+1) * grafo.width + x);

            if(v0.getValor().getBlue() == 0) v0 = null;
            if(v1.getValor().getBlue() == 0) v1 = null;
            if(v2.getValor().getBlue() == 0) v2 = null;
            if(v3.getValor().getBlue() == 0) v3 = null;

            if(v2 == null) { v2 = v3; v3 = null;}
            if(v1 == null) { v1 = v2; v2 = v3; v3 = null;}
            if(v0 == null) { v0 = v1; v1 = v2; v2 = v3; v3 = null;}

            final var _v0 = v0;
            final var _v1 = v1;
            final var _v2 = v2;
            final var _v3 = v3;

            // Atravessa todos os pixels da imagem
            return new Iterator<GrafoVertice<RGBPixel>>() {                
                int index = 0;
                @Override
                public boolean hasNext() {
                    if(index == 0) return _v0 != null;
                    if(index == 1) return _v1 != null;
                    if(index == 2) return _v2 != null;
                    if(index == 3) return _v3 != null;
                    return false;
                }

                @Override
                public GrafoVertice<RGBPixel> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    
                    if(index == 0) { index++; return _v0; }
                    if(index == 1) { index++; return _v1; }
                    if(index == 2) { index++; return _v2; }
                    if(index == 3) { index++; return _v3; }
                    return null;
                }
            };
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
