package br.erickweil.utilidades;

public class Vector2 {
	public float x;
	public float y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vector2 add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2 sub(Vector2 other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector2 add(float other) {
		this.x += other;
		this.y += other;
		return this;
	}
	
	public Vector2 sub(float other) {
		this.x -= other;
		this.y -= other;
		return this;
	}
	
	public Vector2 div(float other) {
		this.x /= other;
		this.y /= other;
		return this;
	}
	
	public Vector2 mul(float other) {
		this.x *= other;
		this.y *= other;
		return this;
	}
	
	public float sqrMagnitude() {
		return x*x + y*y;
	}
	
	public float magnitude() {
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public Vector2 normalize() {
		float mag = this.magnitude();
		this.x /= mag;
		this.y /= mag;
		return this;
	}
	
	public float dot(Vector2 other) {
		return this.x * other.x + this.y * other.y;
	}
	
	public static Vector2 add(Vector2 a, Vector2 b) {
		return new Vector2(a).add(b);
	}
	
	public static Vector2 sub(Vector2 a, Vector2 b) {
		return new Vector2(a).sub(b);
	}
	
	public static Vector2 add(Vector2 a, float b) {
		return new Vector2(a).add(b);
	}
	
	public static Vector2 sub(Vector2 a, float b) {
		return new Vector2(a).sub(b);
	}
	
	public static Vector2 div(Vector2 a, float b) {
		return new Vector2(a).div(b);
	}
	
	public static Vector2 mul(Vector2 a, float b) {
		return new Vector2(a).mul(b);
	}
	
	public static float dot(Vector2 a, Vector2 b) {
		return a.dot(b);
	}
	
	public static Vector2 median(Vector2 a, Vector2 b) {
		return Vector2.add(a, b).div(2.0f);
	}
	
	public static Vector2 normalize(Vector2 v) {
		return new Vector2(v).normalize();
	}
}
