package tancky;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankDeadMsg implements Msg {
	int msgType = Msg.TANK_DEAD_MSG ;
	
	int id ; 
	
	TankClient tc ; 
	
	
	public TankDeadMsg (int id ){
		this.id = id ;
	}
	
	
	public TankDeadMsg (TankClient tc){
		this.tc = tc ; 
	}
	
	
	
	
	

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
		DataOutputStream dos = new DataOutputStream(baos) ;
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
			dos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] buf = baos.toByteArray();
		
		DatagramPacket dp = new DatagramPacket (buf , buf.length , new InetSocketAddress(IP , udpPort)) ; 
		try {
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			
			if(tc.myTank.id == id){
				return ;
			}
			
			for(int i = 0 ; i < tc.enemyTanks.size() ; i ++){
				Tank t = tc.enemyTanks.get(i);
				if(t.id == id){
					t.setLive(false);
					break;
				}
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
