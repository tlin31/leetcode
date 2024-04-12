Desgin the game Blackjack.

For a given deck of cards, there are two people playing. The dealer and the player. The dealer takes 
two cards and one of them is hidden. The player gets two cards after that he can choose to HIT or STAND. 
Get one more card or give dealer the move. The dealer makes a move if his total value of card if less 
than 17.

The first person to reach 21 wins. All face cards are worth 10 except for ACe which can be 1 or 11 
depending on the situvation.


https://leetcode.com/discuss/interview-question/object-oriented-design/159168/design-blackjack-game


Solution:


A possible solution includes the following classes:

Card: one single card
Deck: a deck of cards
Player: abstract class representing players
BlackjackPlayer: specific player
Dealer: other kind of player
Move: a move made by one of the players
BlackjackGame: embeds game logic


Class Card :

	public class Card {
		public enum Suit {TREFLE, CARREAU, PIQUE, COEUR}
		// first 8 count of ordinal+1, others for 10 points.
		public enum Point {C_ACE_1, C_2, C_3, C_4, C_5, C_6, C_7, C_8, C_9, C_10, C_JACK, C_QUEEN, C_KING} 
		public static final int FIRST_SUIT_ORDINAL=10;
	
		private Suit suit;
		private Point point;
		
		public Card(Suit s, Point p) {
			this.point =  p;
			this.suit = s;
		}
	
		public Suit getSuit() {
			return suit;
		}
	
		public Point getPoint() {
			return point;
		}
		
		public int getPoints() {
			// here, we assume that C_ACE counts 1 point. Some extra logic in Player class
			// handles the case where C_ACE may be worth 11
			if (getPoint().ordinal() <= Point.C_10.ordinal()) return getPoint().ordinal()+1;
			else return 10; // suits		
		}
		
		public String toString() {
			return suit.name() + "-" + point.name();
		}
	}

	
Class Deck :

	public class Deck {
		List<Card> cards = new ArrayList<>();
		
		public Deck() {
			createDeck();
			shuffle();
		}
		public void createDeck() {
			// create a deck by inserting one card of each Point for each Suit
			for (Suit s: Suit.values()) {
				for (Point p: Point.values()) {
					cards.add(new Card(s, p));
				}
			}
		}
		
		public void shuffle() {
			Collections.shuffle(cards);
		}
		
		public Card removeOneCard() {
			// take the last card off the deck (the top one)
			if (cards.size() == 0) throw new RuntimeException("No more cards");
			Card card = cards.remove(cards.size()-1);
			return card;
		}
	}
Abstract Class Player :

	public abstract class Player {
		private String name;
		private List<Card> hand = new ArrayList<>();
		abstract boolean canPlay();
		abstract boolean wantToPlay();
		
		public Player(String name) {
			this.name = name;
			
		}
		public String getName() {
			return name;
		}
		
		public int getTotalPoints() {
			// count the points in two ways and select the best for the player 
			int minTotal = 0; // with C_ACE worth 1 point
			int maxTotal = 0; // with C_ACE worth 11 point
			for (Card c: hand) {
				int points = c.getPoints();
				minTotal += points;
				// this would be the count with ACE counting for 11 points
				maxTotal += (c.getPoint() == Point.C_ACE_1)?11:points; 
			}
			// return the most favorable outcome. If considering C_ACE is worth 11 points pushes the 
			// total count beyond 21, return the count where it is worth 1 instead.
			return (maxTotal > 21)?minTotal:maxTotal; 
		}
	
		public void addCard(Card card) {
			hand.add(card);
		}
		
		public String toString() {
			return name;
		}
	}


Class BlackjackPlayer :

	public class BlackjackPlayer extends Player {
		public BlackjackPlayer(String name) {
			super(name);
		}
	
		@Override
		boolean canPlay() {
			return getTotalPoints() < 21;
		}
	
		@Override
		boolean wantToPlay() {
			// here is where the player's strategy could be elaborated. The simple strategy
			// is to keep playing as long as the count is lower than 17.
			return getTotalPoints() < 17;
		}
	}
Class Dealer :

	public class Dealer extends Player{
		public Dealer(String name) {
			super(name);
		}
	
		@Override
		public boolean canPlay() {
			return getTotalPoints() < 21;
		}
	
		@Override
		public boolean wantToPlay() {
			// dealer will keep playing until either he beats the player or goes over.
			return true;
		}
	}


Class Move :

	public class Move {
		// a move is done by the dealer or the player.
		private Player person;
		private Card card;
		public Move(Player p, Card c) {
			this.person = p;
			this.card = c;
		}
		public Player getPerson() {
			return person;
		}
		public Card getCard() {
			return card;
		}
		
		public String toString() {
			return "Move: " + person.getClass().getSimpleName() + " " + person.toString() + " take Card " + card.toString() + " for " + card.getPoints() + " points";
		}
	}


Class BlackjackGame :

	public class BlackjackGame {
		public static void main(String[] args) {
			BlackjackGame g = new BlackjackGame("Samuel"); // Samuel is the player's name
			g.play();
		}
		
		// the deck of cards we will use
		private Deck deckOfCards = new Deck();

		// welcome Steve!
		private Player dealer = new Dealer("Steve");
		private Player player = null;


		// we will record the moves in the order they are played
		// this could be useful for future extensions
		private List<Move> moves = new ArrayList<>();


		// the hidden card held by the dealer at the beginning of the game
		private Card hiddenDealerCard = null;
		
		public BlackjackGame(String playerName) {
			player = new BlackjackPlayer(playerName);
		}
		
		public void play() {
			// the first dealer card
			hiddenDealerCard = deckOfCards.removeOneCard();
			
			// give a card to each player
			giveNewCard(dealer);
			giveNewCard(player);
			
			// let the player play as long as he wants and we are not over
			while (player.canPlay() && player.wantToPlay() && !gameEnded()) {
				giveNewCard(player);
			}
			
			// if the player did not get over (and the game ended), let the dealer play
			if (!gameEnded()) {
				// first, turn the hidden card
				giveCard(dealer, hiddenDealerCard);
				// then play until either wins
				while (dealer.canPlay() && !gameEnded()) {
					giveNewCard(dealer);
				}
			}
			
			// show who won
			showGameWinner();
		}
		
		public void giveNewCard(Player p) {
			giveCard(p, deckOfCards.removeOneCard());
		}
		
		public void giveCard(Player p, Card c) {
			Move move = new Move(p, c);
			moves.add(move);
			p.addCard(move.getCard());
			System.out.println(move.toString() + "   (" + p.getTotalPoints() + ")");
		}
		
		public boolean gameEnded() {
			if (player.getTotalPoints() >= 21) {
				return true;
			} else if (dealer.getTotalPoints() >= 21) {
				return true;
			}
			return false;
		}
		
		public void showGameWinner() {
			if (player.getTotalPoints() >= 21) {
				System.out.println(player.getName() + " has lost... " + player.getTotalPoints() + " > 21");
			} else if (dealer.getTotalPoints() >= 21) {
				System.out.println(dealer.getName() + " has lost... " + dealer.getTotalPoints() + " > 21");
			}  else {
				Player winner = (player.getTotalPoints() > dealer.getTotalPoints())?player:dealer;
					System.out.println(winner.getName() + " wins... " + winner.getTotalPoints());
			}
			
		}
	}