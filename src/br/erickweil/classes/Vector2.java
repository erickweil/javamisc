package br.erickweil.classes;
import java.util.Locale;

/**
 * Vetor imutável de 2 dimensões. Toda operação retorna um novo vetor,
 * o vetor original nunca é alterado.
 * Convenção dos eixos, igual às coordenadas da tela do Java2D e ao Vector3:
 * x para a direita e y para baixo (por isso UP é (0, -1)).
 * Assim um vetor (x, y) pode ser desenhado direto na posição (x, y) da tela.
 */
public final class Vector2 {
    public final double x;
    public final double y;

    /**
     * Cria um vetor com os componentes informados.
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Propriedades estáticas (por serem imutáveis podem ser reutilizadas/compartilhadas sem problemas)
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 ONE = new Vector2(1, 1);
    public static final Vector2 UP = new Vector2(0, -1);   // para cima na tela
    public static final Vector2 DOWN = new Vector2(0, 1);  // para baixo na tela
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);

    /**
     * Soma este vetor com outro, componente a componente.
     * a + b
     */
    public Vector2 add(Vector2 other) {
        return new Vector2(
            this.x + other.x,
            this.y + other.y
        );
    }

    /**
     * Subtrai outro vetor deste, componente a componente.
     * b.subtract(a) é o vetor que vai do ponto a até o ponto b.
     */
    public Vector2 subtract(Vector2 other) {
        return new Vector2(
            this.x - other.x,
            this.y - other.y
        );
    }

    /**
     * Aplica um fator de escala ao vetor, equivalente a multiplicar cada componente
     */
    public Vector2 multiply(double scale) {
        return new Vector2(
            this.x * scale,
            this.y * scale
        );
    }

    /**
     * Divide cada componente do vetor pelo divisor.
     * Lança IllegalArgumentException se o divisor for zero.
     */
    public Vector2 divide(double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("O divisor não pode ser zero.");
        return new Vector2(
            this.x / divisor,
            this.y / divisor
        );
    }

    /**
     * Calcula o produto escalar/interno (dot product) entre este vetor e outro vetor.
     * a . b
     */
    public double dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Calcula o produto vetorial (cross product) em 2D, que é um número e não um vetor:
     * o componente z de new Vector3(a.x, a.y, 0).cross(new Vector3(b.x, b.y, 0)).
     * Como o y cresce para baixo, na tela: positivo se o outro vetor está no sentido horário deste,
     * negativo se anti-horário e zero se forem paralelos. Ex: RIGHT.cross(DOWN) == 1
     */
    public double cross(Vector2 other) {
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Calcula a magnitude (tamanho) do vetor.
     */
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Calcula a magnitude ao quadrado do vetor.
     * Útil para comparações de tamanho sem precisar calcular a raiz quadrada.
     */
    public double magnitudeSquared() {
        return this.x * this.x + this.y * this.y;
    }

    /**
     * Normaliza o vetor, retornando um vetor unitário na mesma direção.
     * Se o vetor for nulo, retorna ZERO.
     */
    public Vector2 normalize() {
        double mag = this.magnitude();
        if (mag == 0) return Vector2.ZERO;
        return this.divide(mag);
    }

    /**
     * Calcula a distância entre este vetor e outro vetor.
     */
    public double distanceTo(Vector2 other) {
        return this.subtract(other).magnitude();
    }

    /**
     * Calcula a distância ao quadrado entre este vetor e outro vetor.
     * Útil para comparações de distância sem precisar calcular a raiz quadrada.
     */
    public double distanceSquaredTo(Vector2 other) {
        Vector2 diff = this.subtract(other);
        return diff.magnitudeSquared();
    }

    /**
     * Verifica se cada componente difere do outro vetor em no máximo epsilon.
     * Use este método em vez de equals para comparar resultados de contas com double.
     */
    public boolean isAlmostEqual(Vector2 other, double epsilon) {
        return Math.abs(this.x - other.x) <= epsilon &&
               Math.abs(this.y - other.y) <= epsilon;
    }

    /**
     * Calcula a projeção deste vetor sobre outro vetor (a "sombra" deste vetor na direção do outro).
     * Se o outro vetor for nulo, retorna ZERO.
     */
    public Vector2 projectOnto(Vector2 other) {
        double dotProduct = this.dot(other);
        double otherMagnitudeSquared = other.magnitudeSquared();
        if (otherMagnitudeSquared == 0) return Vector2.ZERO;
        double scale = dotProduct / otherMagnitudeSquared;
        return other.multiply(scale);
    }

    /**
     * Reflete este vetor em relação a uma superfície, como uma bola quicando.
     * A normal deve ser unitária (use normalize()), caso contrário o resultado sai errado.
     */
    public Vector2 reflect(Vector2 normal) {
        // R = V - 2 * (V . N) * N
        double dotProduct = this.dot(normal);
        return this.subtract(normal.multiply(2 * dotProduct));
    }

    /**
     * Interpolação linear entre este vetor e o alvo.
     * t = 0 retorna este vetor, t = 1 retorna o alvo e t = 0.5 o ponto médio.
     * Valores de t fora de [0, 1] extrapolam além dos dois pontos.
     */
    public Vector2 lerp(Vector2 target, double t) {
        return this.multiply(1 - t).add(target.multiply(t));
    }

    /**
     * Limita cada componente ao intervalo [min, max].
     * Atenção: limita os componentes, não a magnitude do vetor.
     */
    public Vector2 clamp(double min, double max) {
        return new Vector2(
            Math.max(min, Math.min(max, this.x)),
            Math.max(min, Math.min(max, this.y))
        );
    }

    /**
     * Retorna um vetor com o valor absoluto de cada componente.
     */
    public Vector2 abs() {
        return new Vector2(
            Math.abs(this.x),
            Math.abs(this.y)
        );
    }

    /**
     * Retorna o vetor com o sentido invertido, equivalente a multiplicar por -1.
     */
    public Vector2 negate() {
        return new Vector2(-this.x, -this.y);
    }

    // Para poder comparar vetores com equals
    // Atenção: compara os valores exatos, então 0.0 e -0.0 são considerados diferentes
    @Override
    public boolean equals(Object arg0) {
        if (this == arg0) return true;
        if (arg0 == null || getClass() != arg0.getClass()) return false;
        Vector2 other = (Vector2) arg0;
        return Double.compare(this.x, other.x) == 0 &&
               Double.compare(this.y, other.y) == 0;
    }

    // Para poder usar em HashMaps ou HashSets
    @Override
    public int hashCode() {
        int result = Double.hashCode(this.x);
        result = 31 * result + Double.hashCode(this.y);
        return result;
    }

    // Para poder imprimir o vetor de forma legível no console
    // Locale.US garante o ponto como separador decimal, independente do idioma do sistema
    @Override
    public String toString() {
        return String.format(Locale.US, "Vector2(%.2f, %.2f)", this.x, this.y);
    }
}
