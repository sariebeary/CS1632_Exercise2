import static org.junit.Assert.*;

import org.junit.*;

public class LaboonCoinTest {

    // Re-usable LaboonCoin reference.
    LaboonCoin _l;

    // Create a new LaboonCoin instance before each test.
    @Before
    public void setup() {
	_l = new LaboonCoin();
    }

    // Assert that creating a new LaboonCoin instance
    // does not return a null reference
    @Test
    public void testLaboonCoinExists() {
	assertNotNull(_l);
    }

    // Creating a block String with valid data should return
    // a valid block.  This includes printing data out
    // normally, the previous hash and current hash as a hex
    // representations of themself, and the nonce in hex
    // repretentation.  Values should be delimited by
    // pipes.
    @Test
    public void testCreateBlockValid() {
	int prevHash = 0x0;
	int nonce = 0x16f2;
	int hash = 0x545ac;
	String validBlock = _l.createBlock("kitten", prevHash, nonce, hash);
	assertEquals("kitten|00000000|000016f2|000545ac", validBlock);
    }

    // Trying to represent a blockchain which has no blocks
    // in it should just return an empty string.
    @Test
    public void testGetBlockChainNoElements() {
	// By default, _l.blockchain has no elements in it.
	// So we can just test it immediately.
	String blockChain = _l.getBlockChain();
	assertEquals("", blockChain);
    }


    // Viewing the blockchain as a full string which has valid
    // elements should include all of the elements.  Note that the
    // final element DOES have a trailing \n!
    @Test
    public void testGetBlockChainElements() {
	_l.blockchain.add("TESTBLOCK1|00000000|000010e9|000a3cd8");
	_l.blockchain.add("TESTBLOCK2|000a3cd8|00002ca8|0008ff30");
	_l.blockchain.add("TESTBLOCK3|0008ff30|00002171|0009f908");
	String blockChain = _l.getBlockChain();
	assertEquals("TESTBLOCK1|00000000|000010e9|000a3cd8\nTESTBLOCK2|000a3cd8|00002ca8|0008ff30\nTESTBLOCK3|0008ff30|00002171|0009f908\n", blockChain);
    }

    // TODO - PUT YOUR SIX TESTS HERE
	
	// The hash of an empty string or null have a valid hash 0x00989680
	@Test
	public void testHashNull() {
		int laboonHash = _l.hash("");
		int expectedHash = 0x00989680;
		assertEquals(expectedHash, laboonHash);
	}
	
	// The hash of a string should produce the corresponding hash as defined by the LaboonHash algorithm
	// "laboon" (no quotes) hashes to 0x4e4587d6
	@Test
	public void testHashFunction() {
		int laboonHash = _l.hash("laboon");
		int expectedHash = 0x4e4587d6;
		assertEquals(expectedHash, laboonHash);
	}
	
	// The hash of the same string should be equal 
	@Test
	public void testHashEqual() {
		int laboonHash = _l.hash("bill");
		int laboonHash2 = _l.hash("bill");
		assertEquals(laboonHash, laboonHash2);
	}
	
	// A hash is not valid if there are not enough leading zeros to match the difficulty regardless of trailing zeros
	// 0x00ab0000 is NOT valid - despite having 6 0's, there are not enough at the beginning 
	@Test
	public void testValidTrailing() {
		int hashVal = 0x00ab0000;
		int difficulty = 3; 
		assertFalse(_l.validHash(difficulty, hashVal));
	}
	
	// A hash with more leading zeros than difficulty is still valid
	// 0x000000d4 is valid with more leading zeros than difficulty
	@Test
	public void testValidExtraLeading() {
		int hashVal = 0x000000d4;
		int difficulty = 1; 
		assertTrue(_l.validHash(difficulty, hashVal));
	}
	
	// A hash with exactly the correct leading number of zeros as difficulty is valid
	// 0x0000d98a IS valid - it has four 0's at the beginning
	@Test
	public void testValidExact() {
		int hashVal = 0x0000d98a;
		int difficulty = 4; 
		assertTrue(_l.validHash(difficulty, hashVal));
	}
}
