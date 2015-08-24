/*
 * CS61C Spring 2014 Project2
 * Reminders:
 *
 * DO NOT SHARE CODE IN ANY WAY SHAPE OR FORM, NEITHER IN PUBLIC REPOS OR FOR DEBUGGING.
 *
 * This is one of the two files that you should be modifying and submitting for this project.
 */
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class SolveMoves {
    public static class Map extends Mapper<IntWritable, MovesWritable, IntWritable, ByteWritable> {
        /**
         * Configuration and setup that occurs before map gets called for the first time.
         *
         **/
        @Override
        public void setup(Context context) {
        }

        /**
         * The map function for the second mapreduce that you should be filling out.
         */
        @Override
        public void map(IntWritable key, MovesWritable val, Context context) throws IOException, InterruptedException {
            /* YOUR CODE HERE */
            byte child = val.getValue();
        	for (int parent : val.getMoves()) {
        		context.write(new IntWritable(parent), new ByteWritable(child)); // value child representation
        	}
        }
    }

    public static class Reduce extends Reducer<IntWritable, ByteWritable, IntWritable, MovesWritable> {

        int boardWidth;
        int boardHeight;
        int connectWin;
        boolean OTurn;
        /**
         * Configuration and setup that occurs before map gets called for the first time.
         *
         **/
        @Override
        public void setup(Context context) {
            // load up the config vars specified in Proj2.java#main()
            boardWidth = context.getConfiguration().getInt("boardWidth", 0);
            boardHeight = context.getConfiguration().getInt("boardHeight", 0);
            connectWin = context.getConfiguration().getInt("connectWin", 0);
            OTurn = context.getConfiguration().getBoolean("OTurn", true);
        }

        /**
         * The reduce function for the first mapreduce that you should be filling out.
         */
        @Override
        public void reduce(IntWritable key, Iterable<ByteWritable> values, Context context) throws IOException, InterruptedException {
            /* YOUR CODE HERE */
        	boolean foundWin = false, hasValidParent = false, tied = false; // tied is relevant only if foundWin is false
        	ArrayList<Byte> storage = new ArrayList<Byte>();
        	for (ByteWritable val : values) {
        		byte temp = val.get(), status1 = (byte) (temp & 3);
        		storage.add(temp);
        		if ((OTurn && status1 == 1) || (!OTurn && status1 == 2)) foundWin = true;
        		if (temp < 4) hasValidParent = true;
        		if (status1 == 3) tied = true;
        	}
        	byte best = 0; // initializing; will be clobbered anyways based on boolean flags
        	byte bestNumMoves = foundWin ? (byte) Byte.MAX_VALUE : -1;
        	if (hasValidParent) {
        	    for (int i = 0; i < storage.size(); i++) {
        	        Byte temp = storage.get(i);
        	        byte moves = (byte) (temp >>> 2), status = (byte) (temp & 3);
        	        if (foundWin) {
        	            /* Finds byte that gives minimum movesToEnd that also gives a win for whoever's turn it is. */
        	            if ((moves < bestNumMoves) && ((status == 1 && OTurn) || (status == 2 && !OTurn))) {
        	                best = temp;
        	                bestNumMoves = moves;
        	            }
        	        } else if (tied) {
        	            if (moves > bestNumMoves && status == 3) {
        	                best = temp;
        	                bestNumMoves = moves;
        	            }
        	        } else {
        	            if (moves > bestNumMoves /*&& status == 2*/) { // you know status == 2 here by pigeonhole principle. status != 0 for this function.
        	                best = temp;
        	                bestNumMoves = moves;
        	            }
        	        }
        	    }
        		ArrayList<Integer> storing = new ArrayList<Integer>();
        		char token = OTurn ? 'X' : 'O';
        		String board = Proj2Util.gameUnhasher(key.get(), boardWidth, boardHeight);
        		for (int i = 0; i < boardWidth * boardHeight; i += boardHeight) {
        		    for (int j = boardHeight - 1; j >= 0; j--) { // scan from top to bottom
        		        if (board.charAt(i + j) != ' ') {
        		            if (board.charAt(i + j) == token) { // unsure, i think
        		                StringBuilder gameBoard = new StringBuilder(board);
        		                gameBoard.setCharAt(i + j, ' ');
        		                storing.add(Proj2Util.gameHasher(gameBoard.toString(), boardWidth, boardHeight));
        		            }
        		            break; // doesn't make sense to look at things under top layer of column, so break right away
        		        }
        		    }
        		}
        		int[] res = new int[storing.size()];
        		for (int i = 0; i < storing.size(); i++) {
        		    res[i] = storing.get(i);
        		}
        		MovesWritable newLevel = new MovesWritable((byte) (best + 4), res);
        		context.write(key, newLevel);
        	}
        }
    }
}
