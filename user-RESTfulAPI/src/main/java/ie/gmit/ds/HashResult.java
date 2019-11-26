package ie.gmit.ds;

public class HashResult{
    private byte[] hashedPassword;
    private byte[] salt;

    public HashResult(byte[] hashedPassword, byte[] salt){
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public byte [] getHashedPw(){
        return this.hashedPassword;
    }

    public byte [] getSalt(){
        return this.salt;
    }
}