package bgu.spl.a2;//package bgu.spl.a2;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PromiseTest {
	
	
	
	@Test
	public void testResolve(){
		try{
			bgu.spl.a2.Promise<Integer> p = new bgu.spl.a2.Promise<>();
			p.resolve(5);
			try{
				p.resolve(6);
				Assert.fail();
			}
			catch(IllegalStateException ex){
				int x = p.get();
				assertEquals(x, 5);
			}
			catch(Exception ex){
				Assert.fail();
			}
		}
		catch(Exception ex){
			Assert.fail();
		}
	}
	
	@Test
	public void testGet() {
		int x;
		try{
			bgu.spl.a2.Promise<Integer> p = new bgu.spl.a2.Promise<>();
			try{
				x = p.get();
				Assert.fail();
			}
			catch(IllegalStateException ex){
				p.resolve(5);
				x = p.get();
				assertEquals(x, 5);
			}
		}
		catch(Exception ex){
			Assert.fail();
		}
	}
	
	@Test
	public void testIsResolved(){
		try{
			bgu.spl.a2.Promise<Integer> p = new bgu.spl.a2.Promise<>();
			boolean b = p.isResolved();
			assertTrue(!b);
			try{
				p.resolve(5);
				b = p.isResolved();
				assertTrue(b);
			}
			catch(Exception ex){
				Assert.fail();
			}
		}
		catch(Exception ex){
			Assert.fail();
		}
	}
	
	@Test
	public void testSubscribe(){
		bgu.spl.a2.Promise<Integer> p = new bgu.spl.a2.Promise<>();
		bgu.spl.a2.callback c = new bgu.spl.a2.callback() {
			
			@Override
			public void call() {
				throw new RuntimeException();
				
			}
		};
		try{
			p.subscribe(c);
		}
		catch(Exception ex){
			Assert.fail();
		}
		try{
			p.resolve(5);
			Assert.fail();
		}
		catch(Exception ex){}
		try{
			p.subscribe(c);
			Assert.fail();
		}
		catch(Exception ex){}
	}

}
