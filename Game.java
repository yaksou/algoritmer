

import java.util.*;
public class Game
{


   static ArrayList<Player> lotteri;
   static int [] draw;
   static int lotterisavings = 0; // pengar som lotteriet fr�n det tilldelat
   static int gamesavings = 0;    // pengar lotteriet samlar f�r varje dragning

   public static void main( String [] arg)
   {
      Scanner scan =new Scanner(System.in);  
     
      
    
      printMenu();
      int choice = scan.nextInt();
      scan.nextLine(); 
   
      while 
      (choice != 0)
      {
         dispatch(choice);
         printMenu();
         choice = scan.nextInt();
         scan.nextLine();  
      }
      
   
   }// slut main
  
  
  
   public static void dispatch(int choice)
   {
   
      switch(choice)
      {
         case 0:
            { 
               System.out.println("Spelet avslutat");
               System.exit(0);
               break;
            }
         case 1:
            {
               lotteri=new ArrayList<Player>();
               draw=new int [10];
               gamesavings=0;
            
            	// startar ny lotteriet och lottodragning
                              	               			
               break;
            }
         case 2:
            {
            	System.out.println("Vad �r ditt namn?");
            	
            	// Skapar en scanner för att ta användarens input för att skapa den nya spelaren.
            	Scanner scan = new Scanner(System.in);
            	Player newGuy = new Player(scan.nextLine());
            	lotteri.add(newGuy);
            	
            	// Frågar hur många lottorader som spelaren vill ha och lägger till så många.
            	System.out.println("Hur m�nga lottorader �nskas?");
            	newGuy.addLottoraws(scan.nextInt());     	
            	
            	// 20 kr insatts.
            	newGuy.setBet(20);
            	
            	gamesavings += newGuy.getBet();
            	
            	break;
            }	 
         case 3:
            {
            	// Skapar en 10-radig lottodragning.
            	draw = Play.createLottorow(10);
            	
            	System.out.println("Lotto dragningens nummer �r: \n" + Arrays.toString(draw));
            	        
            	break;
            }
         	
         case 4:
            {
            	// En array för att hålla koll på hur många lottorader som vunnit med x-rätt.
            	int[] totalWins = new int[3];
            	
            	// Loopar genom alla spelare.
            	for(int i = 0; i < lotteri.size(); i++)
            	{
            		// Tar ut 'guy' från listan.
            		Player guy = lotteri.get(i);
            		
            		// Håller koll på hur många wins 'guy' har av 5,6,7-rätt.
            		int[] wins = new int[3];
            		
            		ArrayList<int []> guyList = guy.getRows();
            		
            		// Loopar genom en spelares lottorader.
            		for(int j = 0; j < guyList.size(); j++)
            		{
            			// Tar ut lottoraden från 'guy'.
            			int[] guyRow = guyList.get(j);
            			
            			// Håller koll på hur många rätt lottoraden har.
            			int correctAmount = 0;
            			
            			// Loopar genom lottoraden.
            			for(int k = 0; k < guyRow.length; k++)
            			{
            				// Loopar genom lottodragningen.
            				for(int l = 0; l < draw.length; l++)
            				{
            					// Om lottoraden har en siffra rätt så öka correct amount.
            					if (draw[l] == guyRow[k])
            					{
            						correctAmount++;
            						break;
            					}
            					
            				}
            				
            			}
            			// Om lottoraden har fler än fem rätt räknas det som en win, och så ökar 
            			// man antalett rätta med ett både total och 'guy'.
            			if( correctAmount >= 5)
            			{
            				wins[correctAmount-5]++;
            				totalWins[correctAmount-5]++;
            				
            				Integer a = new Integer(correctAmount);
            				
            				// Lägger till lottoraden med vinst till 'guy's "rowWins"
            				guy.addRowWin(guyRow);
            				guy.addRowCorrect(a);
            			}

            		}
            		
            		// Lägger till i 'guy' hur många vinster han har.
            		guy.setWins(wins);
            			
            	}
            	
            	// Dessa är för hur mycket ska delas ut till vardera 5,6,7-rad vinnare.
            	double sevenPot = 0;
            	double sixPot = 0;
            	double fivePot = 0;
            	
            	if(totalWins[2] != 0)
            	{
            		// 70% till 7-rader, dela det med hur många 7-vinster som finns.
            		sevenPot = (double)gamesavings*0.7;
            		sevenPot = sevenPot/totalWins[2];
            		
            		// 20% till 6-rader, dela det med hur många 6-vinster som finns.
            		sixPot = (double)gamesavings*0.2;
            		sixPot = sixPot/totalWins[1];
            		
            		// 10% till 5-rader, dela det med hur många 5-vinster som finns.
            		fivePot = (double)gamesavings*0.1;
            		fivePot = fivePot/totalWins[0];
            		
            	} 
            	else if (totalWins[1] != 0)
            	{
            		// 40% till 6-rader, dela det med hur många 6-vinster som finns.
            		sixPot = (double)gamesavings*0.4;
            		sixPot = sixPot/totalWins[1];
            		
            		// 30% till 5-rader, dela det med hur många 5-vinster som finns.
            		fivePot = (double)gamesavings*0.3;
            		fivePot = fivePot/totalWins[0];
            		
            		// 30% till lotterisavings.
            		double lotteryPot = (double)gamesavings*0.3;
            		lotterisavings += (int)lotteryPot;
            	} 
            	else if (totalWins[0] != 0)
            	{
            		// 50% till 5-rader, dela det med hur många 5-vinster som finns.
            		fivePot = (double)gamesavings*0.5;            		
            		
            		// 50% till lotterisavings.
            		double lotteryPot = (double)gamesavings*0.5;
            		lotterisavings += (int)lotteryPot;
            	}
            	else 
            	{
            		// 100% till lotterisavings.
            		lotterisavings += gamesavings;
            	}
            	
            	System.out.println("= = = = = = = = = = = = = = = = = =\nResultat:");
            	
            	// Loopar igenom alla spelare och skriver deras information.
        		for(int i = 0; i < lotteri.size(); i++)
            	{
            		Player guy = lotteri.get(i);
            		int[] guyWins = guy.getWins();
            		
            		// Räknar ut deras vinster.
            		int guyPot = (int)(guyWins[0]*fivePot + guyWins[1]*sixPot + guyWins[2]*sevenPot);
            		
            		guy.setGain(guyPot);
            		
            		// printa ut info.
            		System.out.println(guy.getResult());            		
            	}
        		
        		System.out.println("Lottot f�r :" + lotterisavings );
        		
            	break;
            }
                     
         default:
            System.out.println("Sorry, fel val");
      }
   }


  
  
   public static void printMenu()
   {
      System.out.println("\n  V�lkomna till lotteriet");
      System.out.println("   ====");
      System.out.println("0: Avsluta spel");
      System.out.println("1: Skapa nytt Spel");
      System.out.println("2: Skapa ny Spelare");
      System.out.println("3: K�r lottodragnig");
      System.out.println("4: Visa resultat" );
            
      System.out.print("\nV�lj operation: ");
   }
	


// metoder som kan behövas i denna klass
// skapa och returnera en lotorad:  skapaLottoRad(int antal)
// hitta antalet rätt i en rad
// räkna vinst
// mm

}