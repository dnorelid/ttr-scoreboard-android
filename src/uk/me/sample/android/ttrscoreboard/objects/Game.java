package uk.me.sample.android.ttrscoreboard.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;


public class Game implements Parcelable {
	private BoardRules board;
	private ArrayList<Player> players;
	
	public Game() {
		this.players = new ArrayList<Player>();
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
		for (int i=0; i < this.players.size() ;i++) {
			if (this.players.get(i).colour == player.colour) {
				return i;
			}
		}
		return -1;
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
	
	/**
	 * Load game from storage
	 * @return
	 */
	public static Game load() {
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
		dest.writeParcelableArray((Player[]) players.toArray(), flags);
	}
	
	public Game(Parcel in) {
		board = BoardRules.getBoard(in.readInt());
		Player[] playerArray;
		in.readParcelable(playerArray);
		players = new ArrayList<Player>((ArrayList<Player>) Arrays.asList(playerArray));
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