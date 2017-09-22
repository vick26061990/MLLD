import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collection;


public class Bayesian {

	public static void main(String[] args)throws IOException {
		String filename = "C:/Users/amit2619/Desktop/Assignment 1/DBPedia.small/small_training.txt";
		File textFile = new File(filename);
	
		HashMap<String,HashMap<String,Integer>>wordCount = new HashMap<String,HashMap<String,Integer>>();
		HashMap<String, Integer> wordMap = new HashMap<String,Integer>() ;
		HashMap<String, Integer> labelChart = new HashMap<String,Integer>() ; 
		
		Scanner in = new Scanner(textFile);
	    // Ignore first three lines.
		for(int i=0; i<3 ; i++ )
		{
			String s = in.nextLine();
		}
		
		long startTime = System.currentTimeMillis();
        int value; int counting=1; 
	    //Refinement of the line
    	while (in.hasNextLine()) {
    		
    		String line = in.nextLine();
    		String []words = line.split(" ", 2);
    		String label = words[0];
    		String text1 = words[1];
    		String[]words1 = text1.split(" ", 2);
    		String text2 = words1[1];
    		String[] words2 = text2.split(" ", 2);
    		String text =words2[1];
    		
    		String fine_x2 = text.replaceAll("\\p{P}", " ").replaceAll("\\s+", " ").replaceAll("[0-9]", "").replaceAll("\\s+", " ").replaceAll("\\s{2}"," ");
    		fine_x2 = fine_x2.trim().replaceAll("(\\s)+", "$1" );	
    		String fine_x = fine_x2.toLowerCase(); 
    		
    		 String spx= fine_x;
    	     String stopWords[] ={" very" ," without", " s", "  ","see", " is"," due", " u" ," also", " must", " might", " like"," will", " may", " can", " much", " every", " the", " in", " other", " this", " the", " many", " any", " an", " or", " for", " in", " an", " and", " is", " a" , " about", " above", " after", " again", " against", " all", " am" , " an", " and", " any", " are", " aren’t", " as", " at", " be", " because", " been", " before", " being", " below", " between", " both", " but", " by", " can’t", " cannot", " could", " couldn’t", " did", " didn’t", " do", " does", " doesn’t", " doing", " don’t", " down", " during", " each", " few", " for", " from", " further", " had", " hadn’t", " has", " hasn’t", " have", " haven’t", " he", " he’d", " he’ll", " he’s", " her", " here", " here’s", " hers", " herself", " him", " himself", " his", " how", " how’s", " i ", " i", " i’d", " i’ll", " i’m", " i’ve", " if", " in", " into", " is"," isn’t", " it", " it’s", " its", " itself", " let’s", " me", " more", " most", " mustn’t", " my", " myself", " no", " nor", " not", " of", " off", " on", " once", " only", " ought", " our", " ours", " ourselves"," out", " over", " own", " same", " shan’t", " she", " she’d", " she’ll", " she’s", " should", " shouldn’t", " so", " some", " such", " than"," that", " that’s", " their", " theirs", " them", " themselves", " then", " there", " there’s", " these", " they", " they’d", " they’ll", " they’re", " they’ve"," this", " those", " through", " to", " too", " under", " until", " up", " very", " was", " wasn’t", " we", " we’d", " we’ll", " we’re", " we’ve"," were", " weren’t", " what", " what’s", " when", " when’s", " where", " where’s", " which", " while", " who", " who’s", " whom"," why", " why’s", " with", " won’t", " would", " wouldn’t", " you", "  you’d", " you’ll", " you’re", " you’ve", " your", " yours", " yourself", " yourselves"," Without", " See", " Unless", " Due", " Also", " Must", " Might", " Like", " Will", " May", " Can", " Much", " Every", " The", " I", " en", " Other", " This", " The", " Many", " Any", " An", " Or", " For", " In", " An", " An ", " Is", " A", " About", " Above", " After", " Again", " Against", " All", " Am", " An", " And", " Any", " Are", " Aren’t", " As", " At", " Be", " Because", " Been", " Before", " Being", " can"};
    		 for(int i=0;i<stopWords.length;i++){
    		       if(spx.contains(stopWords[i])){
    		           spx=spx.replaceAll(stopWords[i]+"\\s+", " "); 
    		       }
    		   }
    		
    		 String sp = spx.replaceAll("<[^>]*>", "");
    		 String sp1 = sp.replaceFirst( "\\[[^\\]]+\\]", "").trim();
    		// System.out.println(sp1);
    		 
    		String[] classifier = label.split(","); 
    		for(int i=0; i<classifier.length ; i++)
    		    {
    			classifier[i] = classifier[i].trim();
    	    	}
    		
			for(int q=0 ; q<classifier.length ; q++)
			{
                
				if(labelChart.get(classifier[q]) != null)
				  {
					labelChart.put(classifier[q], labelChart.get(classifier[q])+1 );
				  }
				else
				  {
					labelChart.put(classifier[q], 1 );
				  }	
			}	

                String[] docWords ;
                docWords = sp.split(" "); 
 	    	   
          for(int r=0 ; r< docWords.length ; r++)
               {
        	       if(docWords[r].length() <8)continue;
        	       if( wordCount.get(docWords[r])!= null)
        	    	  {
        	    	    HashMap<String, Integer> tempMap = wordCount.get(docWords[r]);
        	    	          for(int p = 0 ; p < classifier.length ; p++) 
        	    	          {
        	    	              if(tempMap.get(classifier[p]) != null)
        	    	                {
        	    	    	         tempMap.put(classifier[p], tempMap.get(classifier[p]) + 1);
       	    	                }
        	    	              else
        	    	                {
        	    	    	          tempMap.put(classifier[p], 1);
        	    	                }
        	    	                wordCount.put(docWords[r], tempMap);
        	    	                wordMap.put(docWords[r] , 1+ wordMap.get(docWords[r]) );
        	    	         }
        	    	  } 
        	       else
        	          {
        	    	   for(int s=0 ; s< classifier.length ; s++)
        	    	    {
        	    		   HashMap<String, Integer> tempMap =  new HashMap<>();
        	    		   tempMap.put(classifier[s] , 1);
        	    		   wordCount.put(docWords[r], tempMap ); 
        	    		   wordMap.put(docWords[r] , 1);
        	    	    }
        	         }
               }
      }
    	in.close();
    	Iterator iterator = labelChart.keySet().iterator();
        int count=0; 
    	while (iterator.hasNext()) {
    	   String key = iterator.next().toString();
    	   Integer var = labelChart.get(key);
           count = count + var ;
        	}
    	
    	//Prior probability calcuation
    	HashMap<String, Double> labelData = new HashMap<String,Double>() ;
    	Iterator iterate = labelChart.keySet().iterator();
    	while (iterate.hasNext()) {
    	   String key = iterate.next().toString();
    	   Integer var = labelChart.get(key);
    	   Double temp1 =(double)(var / (double)count) ;
    	 //  System.out.println(key + "...." + var + "...." +temp1 );
           labelData.put(key, temp1 );
        	}
    	
    	//Probability of Data given label/classifier
    	HashMap<String,HashMap<String,Double>>wordData = new HashMap<String,HashMap<String,Double>>();
    	Iterator itr = wordCount.keySet().iterator(); 
    	while (itr.hasNext()) 
    	{
    		String key = itr.next().toString(); int counter = 0; 
    		if(wordCount.get(key) != null)
    		    {
    			  if(wordMap.get(key) != null ){counter = wordMap.get(key);
    			 // System.out.println(key +" ++  " +counter);
    			  }
    			  HashMap<String,Integer> temp1 = wordCount.get(key);
    			  Iterator itv = temp1.keySet().iterator(); 
    			       while(itv.hasNext())
    			       {
    			    	   Double temp2;
    			    	   String keyVal = itv.next().toString(); 
    			    	   Integer val_ = temp1.get(keyVal);
    			    	   if(counter !=0){temp2 = (double)(val_/(double)counter);}
    			    	   else 
    			    		   temp2 =0.0 ;
    			    	   HashMap<String,Double> temp3 = new HashMap<String,Double>();
    			    	// System.out.println(key + " : " + keyVal + " : " + temp2  + " (" + val_ + " and " + counter  );
    			    	   temp3.put(keyVal, temp2);
    			    	   wordData.put(key , temp3);
    			       }
    		    }
       }
    	long estimatedTime = System.currentTimeMillis() - startTime;   	
    	System.out.println("Train time :" + estimatedTime);
		
    	//Checking the quality of mapping by counting total number of appearance of a word in Wordcount and matching with wordMap count.
    	int u=1;
    	Iterator itm = wordCount.keySet().iterator();  
    	while (itm.hasNext()) 
    	{
    		String key = itm.next().toString(); int counter = 0; int count_ = 0; 
    		if(wordMap.get(key) != null )count_ = wordMap.get(key);
    		if(wordCount.get(key) != null)	
    		  {
    			HashMap<String,Integer> temp4= wordCount.get(key);
  			    Iterator itw = temp4.keySet().iterator();
  			         while( itw.hasNext() )
  			           {
  			        	 String key_ = itw.next().toString();
  			        	 Integer value_ = temp4.get(key_);
  			        	 counter = counter + value_ ;
  			           }
    		  }
    	}
    	
    	String filename2 = "C:/Users/amit2619/Desktop/Assignment 1/DBPedia.small/small_training.txt";
		File textFile2 = new File(filename2);
		Scanner inner = new Scanner(textFile2);
	    // Ignore first three lines.
		for(int i=0; i<3 ; i++ )
		{
			String sn = inner.nextLine();
		}

int T =labelChart.size();
int success=0 ; int threadnum=0;
String[] labels = new String[T]; 
String[] docWords1; int count1 =0;			
HashMap<String, Double> testData = new HashMap<String, Double>() ;

startTime = System.currentTimeMillis();
while (inner.hasNextLine()) {	
	threadnum++;
	String line1 = inner.nextLine();
	String []words3 = line1.split(" ", 2);
	String label1 = words3[0];
	String text3 = words3[1];
	String[]words4 = text3.split(" ", 2);
	String text4 = words4[1];
	String[] words5 = text4.split(" ", 2);
	String text5 =words4[1];			
	String finer_x2 = text5.replaceAll("\\p{P}", " ").replaceAll("\\s+", " ").replaceAll("[0-9]", "").replaceAll("\\s+", " ").replaceAll("\\s{2}"," ");
	String finer_x = finer_x2.toLowerCase(); 
	finer_x.trim().replaceAll("(\\s)+", "$1" );	

	String[] classify = label1.split(",");
	
	 String sp2x= finer_x;
     String stopWords1[] ={" very" ," without", " s", "  ","see", " is"," due", " u" ," also", " must", " might", " like"," will", " may", " can", " much", " every", " the", " in", " other", " this", " the", " many", " any", " an", " or", " for", " in", " an", " and", " is", " a" , " about", " above", " after", " again", " against", " all", " am" , " an", " and", " any", " are", " aren’t", " as", " at", " be", " because", " been", " before", " being", " below", " between", " both", " but", " by", " can’t", " cannot", " could", " couldn’t", " did", " didn’t", " do", " does", " doesn’t", " doing", " don’t", " down", " during", " each", " few", " for", " from", " further", " had", " hadn’t", " has", " hasn’t", " have", " haven’t", " he", " he’d", " he’ll", " he’s", " her", " here", " here’s", " hers", " herself", " him", " himself", " his", " how", " how’s", " i ", " i", " i’d", " i’ll", " i’m", " i’ve", " if", " in", " into", " is"," isn’t", " it", " it’s", " its", " itself", " let’s", " me", " more", " most", " mustn’t", " my", " myself", " no", " nor", " not", " of", " off", " on", " once", " only", " ought", " our", " ours", " ourselves"," out", " over", " own", " same", " shan’t", " she", " she’d", " she’ll", " she’s", " should", " shouldn’t", " so", " some", " such", " than"," that", " that’s", " their", " theirs", " them", " themselves", " then", " there", " there’s", " these", " they", " they’d", " they’ll", " they’re", " they’ve"," this", " those", " through", " to", " too", " under", " until", " up", " very", " was", " wasn’t", " we", " we’d", " we’ll", " we’re", " we’ve"," were", " weren’t", " what", " what’s", " when", " when’s", " where", " where’s", " which", " while", " who", " who’s", " whom"," why", " why’s", " with", " won’t", " would", " wouldn’t", " you", "  you’d", " you’ll", " you’re", " you’ve", " your", " yours", " yourself", " yourselves"," Without", " See", " Unless", " Due", " Also", " Must", " Might", " Like", " Will", " May", " Can", " Much", " Every", " The", " I", " en", " Other", " This", " The", " Many", " Any", " An", " Or", " For", " In", " An", " An ", " Is", " A", " About", " Above", " After", " Again", " Against", " All", " Am", " An", " And", " Any", " Are", " Aren’t", " As", " At", " Be", " Because", " Been", " Before", " Being", " can"};
	 for(int i=0;i<stopWords1.length;i++){
	       if(sp2x.contains(stopWords1[i])){
	           sp2x=sp2x.replaceAll(stopWords1[i]+"\\s+", " "); 
	       }
	   }
	 
	 String sp2 = sp2x.replaceAll("<[^>]*>", "");
	//System.out.println(sp2);
                docWords1 = sp2.split(" "); 
                Iterator iteration1 = labelChart.keySet().iterator();
            	while (iteration1.hasNext()) {
            		String key = iteration1.next().toString();
                    if(count1 < T){labels[count1] = key ; count1++ ;}
            	    }
            	
            	Double max =-1.0; String markedLabel = " "; int col =0;
            	testData.clear();
            	//System.out.println(sp2);
                for(int k= 0 ; k < T ; k++)
                {  
                	Double res =1.0 ;
                	
                	for( int m=0; m < docWords1.length ;m++)
                	      { 
                		   if(docWords1[m].length() <8)continue;
                	       if(wordData.get(docWords1[m]) != null)
                	          {
                	    	   HashMap<String,Double>trial  = wordData.get(docWords1[m]);
                	    	   if(trial.get(labels[k]) != null)res = res*trial.get(labels[k]);
                	          }
                	      }
                	 if(labelData.get(labels[k]) != null )
                		 {
                		 res = res*labelData.get(labels[k]);
                		 testData.put(labels[k], res) ;
                		 //System.out.println(labels[k] + " -- "  + res);
                		 }
                } 	
                 
                     Iterator iteration2 = testData.keySet().iterator(); 
                 	 while (iteration2.hasNext()) 
                 	 { 
                
                 		 String key = iteration2.next().toString();
                         Double valmax = testData.get(key);
                         if(max < valmax ){max =valmax ; markedLabel = key ;}
                     }  
                    for(int i=0 ;  i < classify.length ; i++)
                      {
                    	 //System.out.println( markedLabel + " ==  " + classify[i]);
                    	 if(markedLabel.equals(classify[i])){success++; break ;}
                      } 
                   // System.out.println(markedLabel + " -- "  + max);
                   // System.out.println("\n\n");
                  
         } 
System.out.println( (double)(success)/threadnum );
estimatedTime = System.currentTimeMillis() - startTime;
System.out.println("Test time :" + estimatedTime);
         inner.close();
         
     }
}
	