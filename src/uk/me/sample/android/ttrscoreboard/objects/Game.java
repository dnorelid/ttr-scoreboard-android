package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


public class Game implements Parcelable {
	private BoardRules board;
	private ArrayList<Player> players;
	private ArrayList<Expansion> expansions;
	
	public Game() {
		this.players = new ArrayList<Player>();
		this.expansions = new ArrayList<Expansion>();
	}
	
	public Game(BoardRules board) {
		this.board = board;
	}
	
	public int playerCount() {
		return players.size();
	}
	
	public void setBoard(BoardRules board) {
		this.board = board;
	}
	
	public BoardRules getBoard() {
		return board;
	}
	
	private int indexOfPlayer(Player player) {
		return indexOfPlayer(player.id);
	}
	private int indexOfPlayer(int playerId) {
		for (int i=0; i < this.players.size() ;i++) {
			if (this.players.get(i).id == playerId) {
				return i;
			}
		}
		return -1;
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}

	public Player getPlayerById(int id) {
		return getPlayer(indexOfPlayer(id));
	}
	
	public void addPlayer(Player player) {
		if (indexOfPlayer(player) == -1) {
			players.add(player);
		}
	}
	
	public void removePlayer(Player player) {
		int position = indexOfPlayer(player);
		if (position > -1) {
			players.remove(position);
		}
	}
	
	public ArrayList<Expansion> getExpansions() {
		return this.expansions;
	}
	
	public Expansion getExpansion(int expansionId) {
		for (int i=0; i < expansions.size() ;i++) {
			if (expansions.get(i).id == expansionId) {
				return expansions.get(i);
			}
		}
		return null;
	}
	
	public void addExpansion(Expansion expansion) {
		this.expansions.add(expansion);
	}
	
	
	public BoardBonus getBonus(int bonusId) {
		return this.board.getBonus(bonusId);
	}
	
	public ArrayList<BoardBonus> getBonuses() {
		return this.board.getBonuses();
	}
	
	/**
	 * Load game from storage
	 * @return
	 */
	public static Game load() {
		// If no game exists, return a new game
		Game game = new Game();
		return game;
	}
	
	/**
	 * Save game to storage
	 */
	public void save() {
		
	}
	
	/**
	 * Delete stored game 
	 */
	public void delete() {
		
	}
	
	// PARCELABLE
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(board.getId());
		dest.writeTypedList(players);
		dest.writeTypedList(expansions);
	}
	
	public Game(Parcel in) {
		this.players = new ArrayList<Player>();
		this.expansions = new ArrayList<Expansion>();

		this.board = BoardRules.getBoard(in.readInt());
		in.readTypedList(players, Player.CREATOR);
		in.readTypedList(expansions, Expansion.CREATOR);
		
		for (int i=0; i < expansions.size() ;i++) {
			Expansion expansion = expansions.get(i);
			this.board.removeBonuses(expansion.removeBonuses);
			this.board.addBonuses(expansion.newBonuses);
		}
	}
	
	public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
		public Game createFromParcel(Parcel in) {
			return new Game(in);
		}

		public Game[] newArray(int size) {
			return new Game[size];
		}
	};

}
