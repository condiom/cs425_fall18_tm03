package cs425_fall18_tm03;


public class Client {
 
    public static void main(String[] args) {
    	
        if (args.length < 2) return;
 
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);

        for(int i=0;i<N;i++){
        	 new ClientThread(i,hostname,port).start();
        }
   
      
    }
}