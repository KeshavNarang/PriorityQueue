import java.lang.*;
import java.util.*;
public class TurnAroundTime
{
	static int numberJobs = 100;
	
	static ArrayList <Integer> FIFOValues = new ArrayList <Integer> ();
	static PriorityQueue <Integer> SJFValues = new PriorityQueue <Integer> ();
	static ArrayList <Integer> RoundRobinValues = new ArrayList <Integer> ();
	
	public static void main (String [] args)
	{
		int [] randomNumbers = new int [numberJobs];
		for (int i = 0; i < numberJobs; i++)
		{
			Random generator = new Random ();
			randomNumbers[i] = generator.nextInt(100);
		}
		
		fillArrays(randomNumbers);
		
		System.out.println(numberJobs + " jobs assigned random amounts of time to complete: ");
		printArray(FIFOValues);
		
		fifoCalculations();
		sjfCalculations();
		roundrobinCalculations(20);
		
		fillArrays(randomNumbers);
		
		roundrobinCalculations(5);
	}
	
	public static void fillArrays (int [] array)
	{
		// Fill the jobs with random amounts of time
		for (int i = 0; i < array.length; i++)
		{
			FIFOValues.add(array[i]);
			SJFValues.add(array[i]);
			RoundRobinValues.add(array[i]);
		}
	}
	
	public static void fifoCalculations ()
	{
		int totalWaitingTimeFIFO = 0;		
		int totalTurnAroundTimeFIFO = 0;
		
		for (int i = 0; i < numberJobs; i++)
		{
			int FIFOValue = FIFOValues.get(0);
			FIFOValues.remove(0);

			totalWaitingTimeFIFO += FIFOValue;			

			totalTurnAroundTimeFIFO += totalWaitingTimeFIFO;
		}
		
		double averageTurnAroundTimeFIFO = totalTurnAroundTimeFIFO/(double)numberJobs;
		
		System.out.print("If done in first-in-first-out fashion, the average turn-around time for each job is: ");
		System.out.printf("%-2.2f \n", averageTurnAroundTimeFIFO);
	}
	
	public static void sjfCalculations ()
	{
		int totalWaitingTimeSJF = 0;
		int totalTurnAroundTimeSJF = 0;
		
		for (int i = 0; i < numberJobs; i++)
		{
			int SJFValue = SJFValues.poll();		
			totalWaitingTimeSJF += SJFValue;
			totalTurnAroundTimeSJF += totalWaitingTimeSJF;
		}
		
		double averageTurnAroundTimeSJF = totalTurnAroundTimeSJF/(double)numberJobs;
		
		System.out.print("If done in shortest-job-first fashion, the average turn-around time for each job is: ");
		System.out.printf("%-2.2f \n", averageTurnAroundTimeSJF);
	}
	
	public static void roundrobinCalculations (int maxProcessingTime)
	{
		int totalTurnAroundTimeRoundRobin = 0;
		int totalWaitingTimeRoundRobin = 0;
		
		while (RoundRobinValues.size() > 0)
		{
			for (int index = 0; index < RoundRobinValues.size(); index++)
			{
				int currentValue = RoundRobinValues.get(index);
								
				if (currentValue <= maxProcessingTime)
				{
					totalWaitingTimeRoundRobin += currentValue;
					totalTurnAroundTimeRoundRobin += totalWaitingTimeRoundRobin;
					RoundRobinValues.remove(index);
					index--;
				}
				
				else
				{
					totalWaitingTimeRoundRobin += maxProcessingTime;
					RoundRobinValues.set(index, currentValue - maxProcessingTime);
				}
			}
		}
		
		double averageTurnAroundTimeRoundRobin = totalTurnAroundTimeRoundRobin/(double)numberJobs;
		
		System.out.print("If done in round-robin fashion with a maximum allowed processing time of " + maxProcessingTime + ", the average turnaround time for each job is: ");
		System.out.printf("%-2.2f \n", averageTurnAroundTimeRoundRobin);
	}		
	
	public static void printArray (ArrayList <Integer> values)
	{
		int startIndex = 0; // Index of first term to be printed on each line
		int numberTermsLine = values.size()/10;  // How many terms will be printed on each line
		
		System.out.println();
		while (startIndex < values.size()) // Start from startIndex (initially 0)
		{
			// Print the next numberTermsLine (20) terms
			for (int i = startIndex; i < numberTermsLine + startIndex; i++) 
			{
				System.out.printf("%-2d", values.get(i));
				System.out.print(" ");
			}
			startIndex += numberTermsLine; // Increase startIndex appropriately
			System.out.println(); // Repeat on next line
		}
		System.out.println();
	}
}