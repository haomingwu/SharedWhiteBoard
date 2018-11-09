import java.io.Serializable;

public class Shape implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int stroke;
	private int R;
	private int G;
	private int B;
	private String txt;
	private String font;
	private String username;
	private boolean fill;
	
	public Shape(int type, int x1, int x2,int y1, int y2, int stroke, int R, int G, int B, String username){
		this.type = type;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.stroke = stroke;
		this.R = R;
		this.G = G;
		this.B = B;
		this.username = username;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public int getR() {
		return R;
	}

	public void setR(int r) {
		R = r;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public int getB() {
		return B;
	}

	public void setB(int b) {
		B = b;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
}
