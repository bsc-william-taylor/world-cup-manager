package framework.core;

public class CollisionEvent implements IEventListener {
	public interface CollisionArray {
		public void collisionID(Integer num);
	}
	
	private OpenglImage Object;
	private OpenglImage Obj;
	private IEvent event;
	private int Number;
	
	public CollisionEvent() {
		Number = -1;
	}
	
	public void surfaces(OpenglImage i, OpenglImage b, int num) {
		Number = num;
		Object = i;
		Obj = b;
	}
	
	public void surfaces(OpenglImage i, OpenglImage b) {
		Object = i;
		Obj = b;
	}
	
	@Override
	public void check(IEventManager manager) {
		Vector2 posTwo = Object.getPosition();
		Vector2 posOne = Obj.getPosition();
		Vector2 szTwo = Object.getSize();
		Vector2 szOne = Obj.getSize();
		
		float x2 = posTwo.getX();
		float x1 = posOne.getX();
		float w2 = (x2 + szTwo.getX());
		float w1 = (x1 + szOne.getX());
		
		if(x1 <= w2 && w1 >= x2) {
			
			float y2 = posTwo.getY();
			float h2 = (y2 + szTwo.getY());
			float y1 = posOne.getY();
			float h1 = (y1 + szOne.getY());
			
			if(y1 <= h2 && h1 >= y2) {
				if(this.event instanceof CollisionArray) {
					((CollisionArray)event).collisionID(Number);
				} 
				
				manager.triggerEvent(event, null);
			}
		} 
	}

	@Override
	public void eventType(IEvent event) {
		this.event = event;
	}
}
