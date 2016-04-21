package sources;

import java.io.IOException;

public class Test extends ZelPage{

	public Test(int country) {
		super(country);
	}
	
	public void loadData() throws IOException{
		this.parseRouteTable("http://www.zelpage.cz/trate/slovensko/trat-101");
		this.parseRouteTable("http://www.zelpage.cz/trate/slovensko/trat-131");
		this.parseRouteTable("http://www.zelpage.cz/trate/rakousko/trat-520");
	}


}
