package framework.opengl;

import framework.math.Matrix;
import framework.math.Vector2;

import static android.opengl.GLES20.*;

public class OpenglLine {
	private OpenglProgram program;
	private OpenglBuffer buffer;
	private Matrix matrix;
	private Vector2 translate;
	private float[] Position;
	private float[] Colour;
	
	public OpenglLine() {
		program = OpenglShaderManager.get().getShader("shaders/line_vs.glsl", "shaders/line_fs.glsl");
		buffer = new OpenglBuffer();
		matrix = new Matrix();
		Colour = new float[4];
		
		translate = new Vector2(0, 0);
	}
	
	public void setPosition(float... positions) {
		Position = new float[positions.length];
		
		for(int i = 0; i < positions.length; i++) {
			this.Position[i] = positions[i];
		}
		
		buffer.insert(Position);
	}
	
	public void translate(float x, float y) {
		translate.setX(x);
		translate.setY(y);
	}
	
	public void update() {
		matrix.pushIdentity();
		matrix.translate(translate.getX(), translate.getY());
	}
	
	public void reset() {
		matrix.pushIdentity();
		translate.set(0, 0);
	}
	
	public void setColour(float r, float g, float b, float a) {
		Colour[0] = r;
		Colour[1] = g;
		Colour[2] = b;
		Colour[3] = a;
	}
	
	public float[] getColour() {
		return Colour;
	}
	
	public void setLineWidth(float size) {
		glLineWidth(size);
	}
	
	public void render() {
		program.startProgram();
		buffer.bindBuffer();

		int arrayID = program.getAttribute("position");
		int colourID = program.getUniform("colour");
		int projectionID = program.getUniform("Projection");
		int matrixID = program.getUniform("ModelView");
	
		glEnableVertexAttribArray(arrayID);
		glUniformMatrix4fv(matrixID, 1, false, matrix.getModelView(), 0);
		glUniformMatrix4fv(projectionID, 1, false, matrix.getProjection(), 0);
		glUniform4fv(colourID, 1, Colour, 0);
		glVertexAttribPointer(arrayID, 2, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_LINE_STRIP, 0, Position.length / 2);
		glDisableVertexAttribArray(arrayID);

		buffer.unbind();
		program.endProgram();
	}
}
