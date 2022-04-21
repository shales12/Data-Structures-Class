package transit;

import java.util.ArrayList;



public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	
	public Transit() { trainZero = null; }

	
	public Transit(TNode tz) { trainZero = tz; }
	
	
	public TNode getTrainZero () {
		return trainZero;
	}

	
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

	    // UPDATE THIS METHOD

		//Setting head of each layer to 0
		TNode walk_head = new TNode();
		walk_head.setLocation(0);
		TNode bus_head = new TNode();
		bus_head.setLocation(0);
		trainZero = new TNode();
		trainZero.setLocation(0);
	
	//setting pointers in order to not lose the head of the nodes
		TNode ptr1 = walk_head;
		TNode ptr2 = bus_head;
		TNode ptr3 = trainZero;
	
	
		for (int i=0; i < locations.length; i++) //traverses through the array of walking points
	{
			TNode a = new TNode(locations[i]); //creates a node for walking point at each index of the array
			ptr1.setNext(a); //the pointer pointing to the head now points to the new node of the array index
			ptr1 = ptr1.getNext(); //increments the pointer
		
	}
		TNode ptr11 = walk_head;
		 
		bus_head.setDown(ptr11);
		for (int i=0; i < busStops.length; i++){
			TNode b = new TNode(busStops[i]);
			ptr2.setNext(b);
			ptr2 = ptr2.getNext();
	
		while (ptr11.getLocation() != busStops[i])
		{
			ptr11 = ptr11.getNext();
		}
	}

		TNode ptr22 = bus_head;
		trainZero.setDown(ptr22);

		for (int i=0; i < trainStations.length; i++){
			TNode c = new TNode(trainStations[i]);
			ptr3.setNext(c);
			ptr3 = ptr3.getNext();
	
		while (ptr22.getLocation() != trainStations[i]){
			ptr22 = ptr22.getNext();
		}

	}

	
		TNode ptrbus = bus_head; //pointer to the bus node (set to 0)
		TNode ptrtrain = trainZero; //pointer to the train node (set to 0)

		while (ptrbus !=null && ptrtrain !=null) //checking if they are not null
		{
			if (ptrbus.getLocation() == ptrtrain.getLocation()){
				ptrtrain.setDown(ptrbus);
				ptrtrain = ptrtrain.getNext();
			}
			ptrbus = ptrbus.getNext();
		}
	

		TNode ptrwalk = walk_head;
		TNode pbus = bus_head;

		while(ptrwalk != null && pbus != null){

			if(ptrwalk.getLocation() == pbus.getLocation()){

				pbus.setDown(ptrwalk);
				pbus = pbus.getNext();
			}
			ptrwalk = ptrwalk.getNext();
		}
	
	}
	
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
	    // UPDATE THIS METHOD
		TNode ptr1 = trainZero;
		TNode prev = null;

		if (ptr1 == null){
			return;
		}

		if (ptr1.getLocation()==station){

			ptr1 = ptr1.getNext();
		}

		while (ptr1 !=null && ptr1.getLocation() != station){

			prev = ptr1;
			ptr1 = ptr1.getNext();
		}

		prev.setNext(ptr1.getNext());

		if(ptr1.getLocation() != station){

			return;
		}
		
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    // UPDATE THIS METHOD
		
		TNode walk_ptr = trainZero.getDown().getDown();


		if (walk_ptr == null) {
			return; 
		}

		TNode bus_ptr = trainZero.getDown();
		TNode bus_ptrPrev = null;

		while (bus_ptr!=null && bus_ptr.getLocation() < busStop){

			bus_ptrPrev = bus_ptr;
			bus_ptr = bus_ptr.getNext();
		}

		while (walk_ptr != null && walk_ptr.getLocation() != busStop){
			walk_ptr = walk_ptr.getNext();
		}

		if (bus_ptr!=null){
			bus_ptrPrev.setNext(new TNode(busStop, bus_ptrPrev.getNext(), walk_ptr));
		}
		
		else{
		bus_ptrPrev.setNext((new TNode(busStop, null, walk_ptr)));
		}
		
	
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {

	    // UPDATE THIS METHOD
		ArrayList<TNode> root = new ArrayList<>();

		TNode ptr = trainZero;

		while (ptr != null){

			if (ptr.getNext() == null){
				root.add(ptr);
				break;
			}

			if ((ptr.getLocation() <= destination) && (ptr.getNext().getLocation()> destination)){
				root.add(ptr);
				break;
			}

			root.add(ptr); ptr = ptr.getNext();
		}

		TNode ptrb = ptr.getDown();

		while (ptrb != null){
 
			if (ptrb.getNext() == null){ 
				
				root.add(ptrb);
				break; 
			}

			if ((ptrb.getLocation() <= destination) && ptrb.getNext().getLocation() > destination) { 
				
				root.add(ptrb);
				break; 
			}
			
			root.add(ptrb);
			ptrb = ptrb.getNext();

		}

		TNode ptrl = ptrb.getDown();
		
		while(ptrl != null){

			if (ptrl.getNext() == null){

				root.add(ptrl);

				break;

			}

			if ((ptrl.getLocation() <= destination) && (ptrl.getNext().getLocation()> destination)){

				root.add(ptrl);

				break;

			}

			root.add(ptrl);

			ptrl = ptrl.getNext();

		}

		return root;

	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {

	    // UPDATE THIS METHOD
		//3 pointers
		//train, bus, walk

		//head for each
		//trainzero
		//content of first train zero
		//new node head train
		//bushead
		TNode Wfirst = new TNode(0, null, null);
		TNode Bfirst = new TNode(0,null,Wfirst);
		TNode headTrain = new TNode(0,null, Bfirst);
		TNode TLast = headTrain;
		TNode BLast = Bfirst;
		TNode WLast = Wfirst;
		TNode pointerT = trainZero.getNext();
		TNode pointerB = trainZero.getDown().getNext();
		TNode pointerW = trainZero.getDown().getDown().getNext();

		while (pointerT!= null){

			int location = pointerT.getLocation();
			TNode TOldLast = TLast;
			TLast = new TNode();
			TLast.setLocation(location);
			TLast.setNext(null);
			TOldLast.setNext(TLast);
			pointerT = pointerT.getNext();

		}

		while (pointerB!= null){

			int location = pointerB.getLocation();
			TNode BOldLast = BLast;
			BLast = new TNode();
			BLast.setLocation(location);
			BLast.setNext(null);
			BOldLast.setNext(BLast);
			pointerB = pointerB.getNext();
		}

		while (pointerW!= null){

			int location = pointerW.getLocation();
			TNode WOldLast = WLast;
			WLast = new TNode();
			WLast.setLocation(location);
			WLast.setNext(null);
			WOldLast.setNext(WLast);
			pointerW = pointerW.getNext();
		}

		TNode Thead = headTrain;
		TNode Bhead = Bfirst;

		while (Bhead != null && Thead !=null){
			if (Bhead.getLocation() == Thead.getLocation()){
				Thead.setDown(Bhead);
				Thead = Thead.getNext();
			}
			Bhead = Bhead.getNext();
		}

		TNode Whead = Wfirst;
		TNode BBhead = Bfirst;

		while (Whead !=null && BBhead !=null){

			if (Whead.getLocation()==BBhead.getLocation()){
				BBhead.setDown(Whead);
				BBhead = BBhead.getNext();
			}
			Whead = Whead.getNext();
		}

		return headTrain;

		
}
			
	private TNode findScooterNode(int destination){
		TNode prev = null;
		TNode curr = trainZero.getDown().getDown();

		while(curr.getLocation() != destination){
			prev = curr;
			curr = curr.getNext();

			if (curr ==null){
				return prev;
			}		
		}

		return curr;
	}

	private TNode findWalkNode(int destination){

		TNode curr = trainZero.getDown().getDown().getDown();
		TNode prev = null;

		while(curr.getLocation() != destination){
			prev = curr;			
			curr = curr.getNext();

			if(curr == null) {
				return prev;
			}
		}
		return curr;
	}







	
	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

	    // UPDATE THIS METHOD
		TNode startScooters = new TNode();

		//it starts off with 0 just like the rest of methods of transport
		startScooters.setLocation(0);
	
		//need a pointer node
		TNode curr = startScooters;
	
		//set the down of the start of the scooters to the corresponding walk node
		startScooters.setDown(trainZero.getDown().getDown());
	
		//first set up the list for the scooters
		for(int i = 0; i<scooterStops.length; i++){

			TNode newNode = new TNode();
			newNode.setLocation(scooterStops[i]);
			curr.setNext(newNode);
			curr = curr.getNext();
		}
	
	
		//set the downs from the bus nodes
		curr = trainZero.getDown();
		curr.setDown(startScooters);
		curr = curr.getNext();

		while(curr != null){

			curr.setDown(findScooterNode(curr.getLocation()));
			curr = curr.getNext();
		}
		
		//set the downs from the scooter nodes
		curr = startScooters;
		while(curr !=null){

			curr.setDown(findWalkNode(curr.getLocation()));
			curr = curr.getNext();
		}
		
		
}
		
// we have to connect the bus layer down to the scooter layer and the scooter layer down to the walking layer
//set down the bus layer

	

		//make bus stops point down to scooter and scooter stops point down to walking

		
		
	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
