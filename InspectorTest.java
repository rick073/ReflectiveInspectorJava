import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.*;

class InspectorTest
{
	TestClass tester;
	Inspector inspector;
	
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private static final PrintStream originalOut = System.out;
	
	@BeforeAll
	public static void setUpStreams()
	{
		System.setOut(new PrintStream(outContent));
	}
	
	@BeforeEach
	void setUp() throws Exception
	{
		tester = new TestClass();
		inspector = new Inspector();
	}
	
	@AfterAll
	public static void tearDown()
	{
		System.setOut(originalOut);
	}
	
	@Test
	void testInspectSuperclass()
	{
		Class c = tester.getClass();
		inspector.inspectSuperclass(c, tester, true, 0);
		String[] outArr = outContent.toString().split("\n");
		assertEquals("SUPERCLASS FOR = TestClass", outArr[0].trim());
		assertEquals("SUPERCLASS = java.lang.Object", outArr[1].trim());
		assertEquals("RECURSING INTO SUPERCLASS.", outArr[2].trim());
		assertEquals("CLASS = java.lang.Object", outArr[3].trim());
		outContent.reset();
	}

	@Test
	void testInspectInterface()
	{
		Class c = tester.getClass();
		inspector.inspectInterface(c, tester, true, 0);
		String[] outArr = outContent.toString().split("\n");
		assertEquals("INTERFACES FOR = TestClass", outArr[0].trim());
		assertEquals("INTERFACE = InterfaceB", outArr[1].trim());
		assertEquals("RECURSING INTO INTERFACE.", outArr[2].trim());
		assertEquals("CLASS = InterfaceB", outArr[3].trim());
		outContent.reset();
	}

	@Test
	void testInspectConstructor()
	{
		Class c = tester.getClass();
		inspector.inspectConstructor(c, tester, 0);
		String[] outArr = outContent.toString().split("\n");
		assertEquals("CONSTRUCTORS FOR = TestClass", outArr[0].trim());
		assertEquals("CONSTRUCTOR NAME = TestClass", outArr[1].trim());
		assertEquals("CONSTRUCTOR EXCEPTIONS = NONE", outArr[2].trim());
		assertEquals("CONSTRUCTOR PARAMETERS = NONE", outArr[3].trim());
		assertEquals("CONSTRUCTOR MODIFIERS = public", outArr[4].trim());
		outContent.reset();
	}

	@Test
	void testInspectMethod()
	{
		Class c = tester.getClass();
		inspector.inspectMethod(c, tester, 0);
		String[] outArr = outContent.toString().split("\n");
		assertEquals("METHODS FOR = TestClass", outArr[0].trim());
		assertEquals("METHOD NAME =  func0", outArr[1].trim());
		assertEquals("METHOD EXCEPTIONS = java.lang.Exception,", outArr[2].trim());
		assertEquals("METHOD PARAMETERS = int, boolean,", outArr[3].trim());
		assertEquals("METHOD RETURN TYPE =  void", outArr[4].trim());
		assertEquals("METHOD MODIFIERS = public", outArr[5].trim());
		outContent.reset();
	}

	@Test
	void testInspectField()
	{
		Class c = tester.getClass();
		inspector.inspectField(c, tester, true, 0);
		String[] outArr = outContent.toString().split("\n");
		assertEquals("FIELDS FOR = TestClass", outArr[0].trim());
		assertEquals("FIELD NAME = val1", outArr[1].trim());
		assertEquals("FIELD TYPE = ClassA", outArr[2].trim());
		assertEquals("MODIFIERS = private", outArr[3].trim());
		//Value for objects not being tested, since the hash code changes in runtime.
		assertEquals("RECURSING INTO FIELD VALUE.", outArr[5].trim());
		assertEquals("CLASS = ClassA", outArr[6].trim());
		
		//Recursion printouts not being tested because they are already being tested elsewhere
		
		assertEquals("FIELD NAME = val2", outArr[outArr.length - 12].trim());
		assertEquals("FIELD TYPE = int", outArr[outArr.length - 11].trim());
		assertEquals("MODIFIERS = public", outArr[outArr.length - 10].trim());
		assertEquals("FIELD VALUE = 42", outArr[outArr.length - 9].trim());
		
		assertEquals("FIELD NAME = val3", outArr[outArr.length - 8].trim());
		assertEquals("FIELD TYPE = [I", outArr[outArr.length - 7].trim());
		assertEquals("MODIFIERS = public", outArr[outArr.length - 6].trim());
		assertEquals("COMPONENT TYPE = int", outArr[outArr.length - 5].trim());
		assertEquals("LENGTH = 3", outArr[outArr.length - 4].trim());
		assertEquals("ARRAY VALUE = 1", outArr[outArr.length - 3].trim());
		assertEquals("ARRAY VALUE = 2", outArr[outArr.length - 2].trim());
		assertEquals("ARRAY VALUE = 3", outArr[outArr.length - 1].trim());
		outContent.reset();
	}
	
	@Test
	void testInspectArray()
	{
		int[] testArr = {1, 2, 3};
		inspector.inspectArray(testArr, true, 0);
		String[] outArr = outContent.toString().split("\n");
		assertEquals("COMPONENT TYPE = int", outArr[0].trim());
		assertEquals("LENGTH = 3", outArr[1].trim());
		assertEquals("ARRAY VALUE = 1", outArr[2].trim());
		assertEquals("ARRAY VALUE = 2", outArr[3].trim());
		assertEquals("ARRAY VALUE = 3", outArr[4].trim());
		outContent.reset();
	}
}
