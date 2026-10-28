package br.erickweil.classes;
import java.util.Locale;

/**
 * Vetor imutável de 3 dimensões. Toda operação retorna um novo vetor,
 * o vetor original nunca é alterado.
 * Convenção dos eixos, igual às coordenadas da tela do Java2D:
 * x para a direita, y para baixo (por isso UP é (0, -1, 0)) e z para dentro da tela, a profundidade.
 * Assim um ponto (x, y, z) é desenhado na posição (x, y) da tela, e quanto maior o z mais longe ele está.
 * Produto vetorial: RIGHT.cross(DOWN) == FORWARD.
 */
public final class Vector3 {
    public final double x;
    public final double y;
    public final double z;

    /**
     * Cria um vetor com os componentes informados.
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Propriedades estáticas (por serem imutáveis podem ser reutilizadas/compartilhadas sem problemas)
    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 ONE = new Vector3(1, 1, 1);
    public static final Vector3 UP = new Vector3(0, -1, 0);      // para cima na tela
    public static final Vector3 DOWN = new Vector3(0, 1, 0);     // para baixo na tela
    public static final Vector3 LEFT = new Vector3(-1, 0, 0);
    public static final Vector3 RIGHT = new Vector3(1, 0, 0);
    public static final Vector3 FORWARD = new Vector3(0, 0, 1);  // para dentro da tela (mais longe)
    public static final Vector3 BACKWARD = new Vector3(0, 0, -1); // para fora da tela (mais perto)

    /**
     * Soma este vetor com outro, componente a componente.
     * a + b
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        );
    }

    /**
     * Subtrai outro vetor deste, componente a componente.
     * b.subtract(a) é o vetor que vai do ponto a até o ponto b.
     */
    public Vector3 subtract(Vector3 other) {
        return new Vector3(
            this.x - other.x,
            this.y - other.y,
            this.z - other.z
        );
    }

    /**
     * Aplica um fator de escala ao vetor, equivalente a multiplicar cada componente
     */
    public Vector3 multiply(double scale) {
        return new Vector3(
            this.x * scale,
            this.y * scale,
            this.z * scale
        );
    }

    /**
     * Divide cada componente do vetor pelo divisor.
     * Lança IllegalArgumentException se o divisor for zero.
     */
    public Vector3 divide(double divisor) {
        if (divisor == 0) throw new IllegalArgumentException("O divisor não pode ser zero.");
        return new Vector3(
            this.x / divisor,
            this.y / divisor,
            this.z / divisor
        );
    }

    /**
     * Calcula o produto escalar/interno (dot product) entre este vetor e outro vetor.
     * a . b
     */
    public double dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * Calcula o produto vetorial (cross product) entre este vetor e outro vetor.
     * a × b
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    /**
     * Calcula a magnitude (tamanho) do vetor.
     */
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Calcula a magnitude ao quadrado do vetor.
     * Útil para comparações de tamanho sem precisar calcular a raiz quadrada.
     */
    public double magnitudeSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /**
     * Normaliza o vetor, retornando um vetor unitário na mesma direção.
     * Se o vetor for nulo, retorna ZERO.
     */
    public Vector3 normalize() {
        double mag = this.magnitude();
        if (mag == 0) return Vector3.ZERO;
        return this.divide(mag);
    }

    /**
     * Calcula a distância entre este vetor e outro vetor.
     */
    public double distanceTo(Vector3 other) {
        return this.subtract(other).magnitude();
    }

    /**
     * Calcula a distância ao quadrado entre este vetor e outro vetor.
     * Útil para comparações de distância sem precisar calcular a raiz quadrada.
     */
    public double distanceSquaredTo(Vector3 other) {
        Vector3 diff = this.subtract(other);
        return diff.magnitudeSquared();
    }

    /**
     * Verifica se cada componente difere do outro vetor em no máximo epsilon.
     * Use este método em vez de equals para comparar resultados de contas com double.
     */
    public boolean isAlmostEqual(Vector3 other, double epsilon) {
        return Math.abs(this.x - other.x) <= epsilon &&
               Math.abs(this.y - other.y) <= epsilon &&
               Math.abs(this.z - other.z) <= epsilon;
    }

    /**
     * Calcula a projeção deste vetor sobre outro vetor (a "sombra" deste vetor na direção do outro).
     * Se o outro vetor for nulo, retorna ZERO.
     */
    public Vector3 projectOnto(Vector3 other) {
        double dotProduct = this.dot(other);
        double otherMagnitudeSquared = other.magnitudeSquared();
        if (otherMagnitudeSquared == 0) return Vector3.ZERO;
        double scale = dotProduct / otherMagnitudeSquared;
        return other.multiply(scale);
    }

    /**
     * Reflete este vetor em relação a uma superfície, como uma bola quicando.
     * A normal deve ser unitária (use normalize()), caso contrário o resultado sai errado.
     */
    public Vector3 reflect(Vector3 normal) {
        // R = V - 2 * (V . N) * N
        double dotProduct = this.dot(normal);
        return this.subtract(normal.multiply(2 * dotProduct));
    }

    /**
     * Interpolação linear entre este vetor e o alvo.
     * t = 0 retorna este vetor, t = 1 retorna o alvo e t = 0.5 o ponto médio.
     * Valores de t fora de [0, 1] extrapolam além dos dois pontos.
     */
    public Vector3 lerp(Vector3 target, double t) {
        return this.multiply(1 - t).add(target.multiply(t));
    }

    /**
     * Limita cada componente ao intervalo [min, max].
     * Atenção: limita os componentes, não a magnitude do vetor.
     */
    public Vector3 clamp(double min, double max) {
        return new Vector3(
            Math.max(min, Math.min(max, this.x)),
            Math.max(min, Math.min(max, this.y)),
            Math.max(min, Math.min(max, this.z))
        );
    }

    /**
     * Retorna um vetor com o valor absoluto de cada componente.
     */
    public Vector3 abs() {
        return new Vector3(
            Math.abs(this.x),
            Math.abs(this.y),
            Math.abs(this.z)
        );
    }

    /**
     * Retorna o vetor com o sentido invertido, equivalente a multiplicar por -1.
     */
    public Vector3 negate() {
        return new Vector3(-this.x, -this.y, -this.z);
    }

    // Para poder comparar vetores com equals
    // Atenção: compara os valores exatos, então 0.0 e -0.0 são considerados diferentes
    @Override
    public boolean equals(Object arg0) {
        if (this == arg0) return true;
        if (arg0 == null || getClass() != arg0.getClass()) return false;
        Vector3 other = (Vector3) arg0;
        return Double.compare(this.x, other.x) == 0 &&
               Double.compare(this.y, other.y) == 0 &&
               Double.compare(this.z, other.z) == 0;
    }

    // Para poder usar em HashMaps ou HashSets
    @Override
    public int hashCode() {
        int result = Double.hashCode(this.x);
        result = 31 * result + Double.hashCode(this.y);
        result = 31 * result + Double.hashCode(this.z);
        return result;
    }

    // Para poder imprimir o vetor de forma legível no console
    // Locale.US garante o ponto como separador decimal, independente do idioma do sistema
    @Override
    public String toString() {
        return String.format(Locale.US, "Vector3(%.2f, %.2f, %.2f)", this.x, this.y, this.z);
    }
}
