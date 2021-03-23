package pendulum;

import java.util.ArrayList;
import processing.core.*;

public class Main extends PApplet{
	public float r1 = random(50, 100);
	public float r2 = random(50, 100);
	public float m1 = random(15, 40);
	public float m2 = random(15, 40);
	public float a1 = PI/2+random(-PI/8, PI/8);
	public float a2 = PI/2+random(-PI/8, PI/8);
	public float a1_v = 0;
	public float a2_v = 0;
	public float g = 1;
	public ArrayList<PVector> trail;
	public int trailCol;
	public int bobCol;
	
	public static void main(String[] args) {
		for(int x=0;x<4;x++) {
			for(int y=0;y<4;y++) {
		    PApplet.main(new String[] {"pendulum.Main"});
			}
		}
	}
	
	public void settings() {
		size(360, 200);
	}
	
	public void setup() {
		trail = new ArrayList<PVector>();
		surface.setLocation((int)random(displayWidth-width), (int)random(displayHeight-height));
		trailCol = color(random(80, 255), random(80, 255), random(80, 255));
		bobCol = color(random(255), random(255), random(255));
	}
	
	public void draw() {
		background(255);
		translate(width/2, 20);
		float num1 = -g * (2 * m1 + m2) * sin(a1);
	  float num2 = -m2 * g * sin(a1-2*a2);
	  float num3 = -2*sin(a1-a2)*m2;
	  float num4 = a2_v*a2_v*r2+a1_v*a1_v*r1*cos(a1-a2);
	  float den = r1 * (2*m1+m2-m2*cos(2*a1-2*a2));
	  float a1_a = (num1 + num2 + num3*num4) / den;

	  num1 = 2 * sin(a1-a2);
	  num2 = (a1_v*a1_v*r1*(m1+m2));
	  num3 = g * (m1 + m2) * cos(a1);
	  num4 = a2_v*a2_v*r2*m2*cos(a1-a2);
	  den = r2 * (2*m1+m2-m2*cos(2*a1-2*a2));
	  float a2_a = (num1*(num2+num3+num4)) / den;
		
		float x1 = r1*sin(a1);
		float y1 = r1*cos(a1);
		float x2 = r2*sin(a2)+x1;
		float y2 = r2*cos(a2)+y1;
		trail.add(new PVector(x2, y2));
		
		push();
		strokeWeight(3);
		for(int i=0;i<trail.size()-1;i++) {
			PVector a = trail.get(i);
			PVector b = trail.get(i+1);
			stroke(trailCol, map(i, 0, trail.size(), 0, 255));
			line(a.x, a.y, b.x, b.y);
		}
		pop();
		if(trail.size()>1500)trail.remove(0);
		
		push();
		fill(bobCol);
		stroke(bobCol);
		strokeWeight(2);
		line(x1, y1, x2, y2);
		line(0, 0, x1, y1);
		ellipse(x1, y1, m1/3, m1/3);
		ellipse(x2, y2, m2/3, m2/3);
		pop();
		
		a1_v += a1_a;
		a2_v += a2_a;
		a1 += a1_v;
		a2 += a2_v;
	}
}