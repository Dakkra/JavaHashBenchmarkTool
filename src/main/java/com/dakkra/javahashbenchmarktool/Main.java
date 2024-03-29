package com.dakkra.javahashbenchmarktool;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

	public static List<Integer> scores;

	public static void main( String[] args ) {
		System.out.println( "Dakkra's Java SHA3 CPU Benchmark Tool" );

		int threadCount = Runtime.getRuntime().availableProcessors();
		System.out.println( "Discovered " + threadCount + " CPU threads" );

		scores = new Vector<>();

		//int size = (int)Math.pow( 2, 20 );
		int size = 4096;
		String data = generateRandomData( size );
		System.out.println( "Generated random data of size " + size );

		System.out.println( "Doing single thread bench..." );
		HashWork single = new HashWork( scores, data );
		single.startWork();
		single.joinThread();
		int singleCoreScore = scores.get( 0 );
		System.out.println( "Single thread score is: " + singleCoreScore );

		scores.clear();
		System.out.println( "Now doing multi-thread test..." );
		ArrayList<HashWork> workers = new ArrayList<>( threadCount );
		for( int i = 0; i < threadCount; i++ ) {
			workers.add( new HashWork( scores, data ) );
		}
		for( HashWork worker : workers ) {
			worker.startWork();
		}
		for( HashWork worker : workers ) {
			worker.joinThread();
		}

		long sum = 0;
		synchronized( scores ) {
			for( Integer score : scores ) {
				sum += score;
			}
		}
		double avg = (double)sum / threadCount;
		System.out.println( "Number of scores: " + scores.size() + " (should match CPU thread count)" );
		System.out.println( "Total score is: " + sum );
		System.out.println( "Improvement over single core: " + (sum / singleCoreScore) );
		System.out.println( "With avg per thread: " + avg );
		System.out.println( "Multi-thread efficiency : " + (avg / (double)singleCoreScore) );
	}

	/**
	 * Generates random data and returns it as a string
	 *
	 * @param size number of bytes to generate, this must be greater than 0
	 * @return String representing random data
	 * @throws IllegalArgumentException if size <= 0
	 */
	public static String generateRandomData( int size ) {

		if( size <= 0 ) {
			throw new IllegalArgumentException( "" );
		}

		Random r = new Random();
		byte[] data = new byte[ size ];
		r.nextBytes( data );

		return new String( data, StandardCharsets.US_ASCII );
	}

}
