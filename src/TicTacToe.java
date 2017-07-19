import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class TicTacToe extends Applet implements ActionListener {

	Button squares[];
	Button newGameButton;
	Label score;
	int emptySquaresLeft=9;
	
	/**
	 * Method init - applet constructor
	 */
	public void init(){
		//Set position manager, font and color
		this.setLayout(new BorderLayout());
		this.setBackground(Color.CYAN);
		
		//make applet`s font bold and 20 size
		Font appletFont=new Font("Monospased",Font.BOLD,20);
		this.setFont(appletFont);
		
		//Create "New Game" button and register in actionlistener
		newGameButton=new Button("New Game");
		newGameButton.addActionListener(this);
		
		Panel topPanel=new Panel();
		topPanel.add(newGameButton);
			
			this.add(topPanel,"North");
			
			Panel centerPanel=new Panel();
			centerPanel.setLayout(new GridLayout(3,3));
			this.add(centerPanel,"Center");
			
			score=new Label("Your turn");
			this.add(score, "South");
			
		//create massiv for references of 9 buttons
			squares=new Button[9];
			
		//create buttons, save those references in massive and
		// register them in listener, color them in orange
		// and add to panel
			
			for(int i=0; i<9;i++){
				squares[i]=new Button();
				squares[i].addActionListener(this);
				squares[i].setBackground(Color.ORANGE);
				centerPanel.add(squares[i]);
			}
		
		}
	
	/**
	 * this method will process all events
	 * @param ActionEvent object
	 */
		public void actionPerformed(ActionEvent e){
			
			Button theButton=(Button) e.getSource();
			
			//if NEWGAME button
			if(theButton==newGameButton){
				for(int i=0;i<9;i++){
					squares[i].setEnabled(true);
					squares[i].setLabel("");
					squares[i].setBackground(Color.ORANGE);
				}
			
			emptySquaresLeft=9;	
			score.setText("Your turn!");
			newGameButton.setEnabled(false);
			return;
			}
			
			String winner="";
			
			//if one of cells
			for (int i=0;i<9;i++){
				if (theButton==squares[i]){
					squares[i].setLabel("X");
					winner=lookForWinner();
						
						if(!"".equals(winner)){
							endTheGame();
						} else {
							computerMove();
							winner=lookForWinner();
							
							if(!"".equals(winner)){
								endTheGame();
							}
						}
					break;
					
				}
			}// end of circle for
		
			if(winner.equals("X")){
				score.setText("You won!");
				} else if (winner.equals("O")){
					score.setText("You lost!");
				} else if (winner.equals("T")){
					score.setText("It`s a tie!");
					}
			
		} //end of actionPerformed method
		
		/**
		 * This method is call after every step to know if there is a winner.
		 * He checks every row, column, diagonal for three identical cells (not empty)
		 * @return "X","O","T"-tie, ""- not yet winner
		 */
		String lookForWinner(){
			String theWinner="";
			emptySquaresLeft--;
			
			if(emptySquaresLeft==0){
				return "T"; //tie
			}
			
			//Check 1 row - elements of massive 0,1,2
			if (!squares[0].getLabel().equals("") &&
					squares[0].getLabel().equals(squares[1].getLabel()) &&
					squares[0].getLabel().equals(squares[2].getLabel()) ){
				theWinner=squares[0].getLabel();
				highlightWinner(0,1,2);
			
				//check 2 row - elements mass 3,4,5
			} else if(!squares[3].getLabel().equals("") &&
					squares[3].getLabel().equals(squares[4].getLabel()) &&
					squares[3].getLabel().equals(squares[5].getLabel())){
				
				theWinner=squares[3].getLabel();
				highlightWinner(3,4,5);
			
			//check row 3 - elements 6,7,8
			} else if(!squares[6].getLabel().equals("") &&
					squares[6].getLabel().equals(squares[7].getLabel()) &&
					squares[6].getLabel().equals(squares[8].getLabel())){
				
				theWinner=squares[6].getLabel();
				highlightWinner(6,7,8);
			
			//check 1 column - elements 0,3,6
			} else if(!squares[0].getLabel().equals("") &&
					squares[0].getLabel().equals(squares[3].getLabel()) &&
					squares[0].getLabel().equals(squares[6].getLabel())){
				
				theWinner=squares[0].getLabel();
				highlightWinner(0,3,6);
			
			//check 2 column - elements 1,4,7
			} else if(!squares[1].getLabel().equals("") &&
					squares[1].getLabel().equals(squares[4].getLabel()) &&
					squares[1].getLabel().equals(squares[7].getLabel())){
				
				theWinner=squares[1].getLabel();
				highlightWinner(1,4,7);
			
			//check 3 column - elements 2,5,8
			} else if(!squares[2].getLabel().equals("") &&
					squares[2].getLabel().equals(squares[5].getLabel()) &&
					squares[2].getLabel().equals(squares[8].getLabel())){
				
				theWinner=squares[2].getLabel();
				highlightWinner(2,5,8);
			
			//check 1 diagonal - elements massive 0,4,8
			} else if(!squares[0].getLabel().equals("") &&
					squares[0].getLabel().equals(squares[4].getLabel()) &&
					squares[0].getLabel().equals(squares[8].getLabel())){
				
				theWinner=squares[0].getLabel();
				highlightWinner(0,4,8);
			
			//check 2 diagonal - elements massive 2,4,6
			} else if(!squares[2].getLabel().equals("") &&
					squares[2].getLabel().equals(squares[4].getLabel()) &&
					squares[2].getLabel().equals(squares[6].getLabel())){
				
				theWinner=squares[2].getLabel();
				highlightWinner(2,4,6);
			}
			return theWinner;
			
			
		}
		
		/**
		 * This method uses rules to find best computer move
		 * if move is not found, then chooses random cell
		 */
		void computerMove(){
			int selectedSquare;
			
			//first, computer tries to find empty cell
			//near two "O" cells to win
			selectedSquare=findEmptySquare("0");
			
			//if he cannot find two "O", the he tries to
			//dont give opponent to make row from 3 "x", and
			//put "O" near "X"
			if (selectedSquare==-1){
				selectedSquare=findEmptySquare("X");
				
			}
			
			//if selectedSquare still equals -1
			//then computer tries to take central cell
			if((selectedSquare==-1) && (squares[4].getLabel().equals("")) ){
				selectedSquare=4;
			}
			
			//if central cell is not empty, take random cell
			if(selectedSquare==-1){
				selectedSquare=getRandomSquare();
			}
			squares[selectedSquare].setLabel("O");
			
		}
		
		/**
		 * This method checks every row,column and diagonal
		 * to know if there are two cells with equal strings and empty cell
		 * @param send X - for user, and O - for computer
		 * @return number of empty cells, or -1, if not found two cells
		 * with equal strings
		 */
		int findEmptySquare(String player){
			int weight[]=new int[9];
			
			for (int i=0;i<9;i++){
				if(squares[i].getLabel().equals("O"))
					weight[i]=-1;
				else if (squares[i].getLabel().equals("X"))
					weight[i]=1;
				else weight[i]=0;
			}
			
			int twoWeights=player.equals("O")?-2:2;
		
			
			//check if in 1 row are two equal cells and one empty
			if(weight[0]+weight[1]+weight[2]==twoWeights){
				if (weight[0]==0) return 0;
				else if(weight[1]==0) return 1;
				else return 2;
			}
			
			//check if in 2 row two equal cells and one empty
			if(weight[3]+weight[4]+weight[5]==twoWeights){
				if (weight[3]==0) return 3;
				else if(weight[4]==0) return 4;
				else return 5;
			}
			
			//check if in 3 row two equal cells and one empty
			if(weight[6]+weight[7]+weight[8]==twoWeights){
				if (weight[6]==0) return 6;
				else if(weight[7]==0) return 7;
				else return 8;
			}
			
			//check if in 1 column two equal cells and one empty
			if(weight[0]+weight[3]+weight[6]==twoWeights){
				if (weight[0]==0) return 0;
				else if(weight[3]==0) return 3;
				else return 6;
			}
			
			//check if in 2 column two equal cells and one empty
			if(weight[1]+weight[4]+weight[7]==twoWeights){
				if (weight[1]==0) return 1;
				else if(weight[4]==0) return 4;
				else return 7;
			}
			
			//check if in 3 column two equal cells and one empty
			if(weight[2]+weight[5]+weight[8]==twoWeights){
				if (weight[2]==0) return 2;
				else if(weight[5]==0) return 5;
				else return 8;
			}
			
			//check if in 1 diagonal two equal cells and one empty
			if(weight[0]+weight[4]+weight[8]==twoWeights){
				if (weight[0]==0) return 0;
				else if(weight[4]==0) return 4;
				else return 8;
			}
			
			//check if in 2 column two equal cells and one empty
			if(weight[2]+weight[4]+weight[6]==twoWeights){
				if (weight[2]==0) return 2;
				else if(weight[4]==0) return 4;
				else return 6;
			}
			
			//if not found two equal cells
			return -1;
		
		}//end of findEmptySquare method
		
		/**
		 * This method chose random empty cell
		 * @return number of cell
		 */
		int getRandomSquare(){
			boolean gotEmptySquare=false;
			int selectedSquare=-1;
			do {
				selectedSquare=(int)(Math.random()*9);
				if (squares[selectedSquare].getLabel().equals("")){
					gotEmptySquare=true;//to end circle
				}
			} while (!gotEmptySquare);
			
			return selectedSquare;
		
		}// end of getRandomSquare method
		
		/**
		 * This method select win line
		 * @param 1,2,3 cells for select
		 */
		void highlightWinner(int win1,int win2,int win3){
			squares[win1].setBackground(Color.CYAN);
			squares[win2].setBackground(Color.CYAN);
			squares[win3].setBackground(Color.CYAN);
		}
		
		//make inaccesible cells and accesible NEWGAME button
		void endTheGame(){
			newGameButton.setEnabled(true);
			for (int i=0;i<9;i++){
				squares[i].setEnabled(false);
			}
		}
		
	} //end of class

