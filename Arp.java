import java.util.HashMap;
import java.util.Map;

class Arp {
    int ip;

    int mac;

    Map<Integer, Integer> arptable;

    // constructor
    public Arp(int mac, int ip) {
        // TODO
        this.mac = mac;
        this.ip = ip;
        this.arptable = new HashMap<>();
        arptable.put(ip, mac);
    }

    // This function returns a spoofed ARP packet:
    //  The argument passed to this function is the IP address that you want to impersonate.
    public String spoofArp(int spoofIP) {
        // TODO
        String opcode = "02";
        String senderMAC = Integer.toHexString(mac);        //"aaaaaa"
        String senderIP = Integer.toHexString(spoofIP);     //"bbbb"
        String targetMAC = "000000";
        String targetIP = senderIP;

        String packet = opcode + senderMAC + senderIP + targetMAC + targetIP;
        return packet;
    }

    // Receive a message and provide the response. This function returns either a packet, or a status code.
    public String receiveArp(String message) {
        // TODO
        int opcode = Integer.parseInt(message.substring(0, 2), 16);
        int senderMac = Integer.parseInt(message.substring(2, 8), 16);
        int senderIp = Integer.parseInt(message.substring(8, 12), 16);
        int targetMac = Integer.parseInt(message.substring(12, 18), 16);
        int targetIp = Integer.parseInt(message.substring(18, 22), 16);

        if (targetIp == this.ip) {
            String topcode = "02";
            String mac = Integer.toHexString(this.mac);
            String ip = Integer.toHexString(this.ip);
            String senderMAC = message.substring(2, 8);
            String senderIP = message.substring(8, 12);

            String ret = topcode + mac + ip + senderMAC + senderIP;
            return ret;
        }

        if (Integer.toHexString(opcode).equals("1") || targetMac != 0) {
            return "IGNORE";
        }
        if (arptable.containsKey(senderIp)) {
            if (arptable.get(senderIp).equals(senderMac)) {
                return "IGNORE";
            } else {
                return "ATTACK";
            }
        }
        arptable.put(senderIp, senderMac);
        return "OK";
    }
}
