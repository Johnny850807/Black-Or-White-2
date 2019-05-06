package com.pokewords.framework.engine;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.engine.exceptions.FiniteStateMachineException;
import com.pokewords.framework.engine.utils.StringUtility;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author johnny850807 (waterball)
 */
public class FiniteStateMachineTest extends AbstractTest {
    //States
    private final String A = "A";
    private final String B = "B";
    private final String C = "C";
    private final String TARGET = "target";
    private final String START = "start";
    private final String END = "end";

    //Events
    private final String N1 = "1";
    private final String N2 = "2";
    private final String N3 = "3";
    private final String EVENT = "event";
    private final String NEXT = "next";
    private final String ASCII = "ascii";
    private final String SHOOT = "shoot";
    private final String FINALIZE = "finalize";

    private final int TEST_LOOP = 30;

    @Test
    public void testShouldReturnNullIfGetCurrentStateIfNoCurrentStateSet(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        assertNull(fsm.getCurrentState());
    }

    @Test
    public void testShouldThrowExceptionIfSetCurrentStateButNotExists(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();

        try{
            fsm.setCurrentState(A); //the 'A' state has not been set -> should throw exception
            fail();
        } catch (FiniteStateMachineException err){
            System.out.println("Expected Exception Thrown: " + err.getMessage());
        }
    }

    @Test
    public void testGetCurrentState(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(A);
        fsm.setCurrentState(A);
        assertEquals(A, fsm.getCurrentState());
    }

    @Test
    public void testShouldThrowExceptionIfNoCurrentStateSetDuringTriggered(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(A);
        fsm.addState(B);
        fsm.addTransition(A, EVENT, B);

        try{
            fsm.trigger(EVENT);
            fail();
        } catch (FiniteStateMachineException err){
            System.out.println("Expected Exception Thrown: " + err.getMessage());
        }
    }

    @Test
    public void testShouldThrowExceptionIfAddTransitionFromNonexistentState(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(B);

        try{
            fsm.addTransition(A, EVENT, B);
            fail();
        } catch (FiniteStateMachineException err){
            System.out.println("Expected Exception Thrown: " + err.getMessage());
        }
    }

    @Test
    public void testShouldThrowExceptionIfAddTransitionToNonexistentState(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(A);

        try{
            fsm.addTransition(A, EVENT, B);
            fail();
        } catch (FiniteStateMachineException err){
            System.out.println("Expected Exception Thrown: " + err.getMessage());
        }
    }

    @Test
    public void testBasicFsmTriggers(){
        FiniteStateMachine<String> fsm = createBasicFsm();

        for (int i = 0; i < TEST_LOOP; i++) {
            assertEquals(A, fsm.getCurrentState());
            assertEquals(B, fsm.trigger(N1));
            assertEquals(B, fsm.getCurrentState());
            assertEquals(C, fsm.trigger(N2));
            assertEquals(C, fsm.getCurrentState());
            assertEquals(A, fsm.trigger(N3));
        }
    }

    @Test
    public void testBasicFsmTriggerRemainsTheCurrentStateIfTransitionNotExists(){
        FiniteStateMachine<String> fsm = createBasicFsm();

        //We add states but don't add transitions
        fsm.addState(A);
        fsm.addState(B);
        fsm.addState(C);

        final String[] EVENTS = {N1, N2, N3};

        //Assert the state will always remain at the current state

        fsm.setCurrentState(A);
        for (int i = 0; i < TEST_LOOP; i++)
            assertRemainsCurrentState(fsm, A, fsm.trigger(EVENTS[i % EVENTS.length]));


        fsm.setCurrentState(B);
        for (int i = 0; i < TEST_LOOP; i++) {
            assertRemainsCurrentState(fsm, B, fsm.trigger(EVENTS[i % EVENTS.length]));
        }

        fsm.setCurrentState(C);
        for (int i = 0; i < TEST_LOOP; i++) {
            assertRemainsCurrentState(fsm, C, fsm.trigger(EVENTS[i % EVENTS.length]));
        }
    }

    private FiniteStateMachine<String> createBasicFsm(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(A);
        fsm.addState(B);
        fsm.addTransition(A, N1, B);

        fsm.addState(C);
        fsm.addTransition(B, N2, C);
        fsm.addTransition(C, N3, A);
        fsm.setCurrentState(A);
        return fsm;
    }

    @Test
    public void testShouldThrowExceptionIfAddTransitionFromAllStatesButTargetStateNotExists(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(A);
        fsm.addState(B);
        fsm.addState(C);

        try{
            fsm.addTransitionFromAllStates(EVENT, TARGET);
            fail();
        } catch (FiniteStateMachineException err){
            System.out.println("Expected Exception Thrown: " + err.getMessage());
        }
    }

    @Test
    public void testShouldThrowExceptionIfAddTransitionFromAllStatesExceptButTargetStateNotExists(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        fsm.addState(A);
        fsm.addState(B);
        fsm.addState(C);

        try{
            fsm.addTransitionFromAllStates(EVENT, TARGET, A, B);
            fail();
        } catch (FiniteStateMachineException err){
            System.out.println("Expected Exception Thrown: " + err.getMessage());
        }
    }

    @Test
    public void testAddTransitionFromAllStates(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();

        // Add states '0', '1', ..., '99'
        for (int i = 0; i < 100; i++) {
            fsm.addState(String.valueOf(i));
        }

        fsm.addState(TARGET);
        fsm.addTransitionFromAllStates(EVENT, TARGET);

        //Assert '0' ~ '99' should all be triggered to TARGET
        for (int i = 0; i < 100; i++)
            assertTriggered(fsm, String.valueOf(i), EVENT, TARGET);
    }

    @Test
    public void testAddTransitionFromAllStatesExcept(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();

        // Add states '0', '1', ..., '99'
        for (int i = 0; i < 100; i++) {
            fsm.addState(String.valueOf(i));
        }

        // Add states 'a', 'b', ..., 'z' (Which should be excluded from adding transitions)
        String[] lowerCaseCharacters = StringUtility.lowerCaseCharacters();
        for (String character : lowerCaseCharacters) {
            fsm.addState(character);
        }

        fsm.addState(TARGET);
        fsm.addTransitionFromAllStates(EVENT, TARGET, lowerCaseCharacters);

        //Assert '0' ~ '99' should all be triggered to TARGET
        for (int i = 0; i < 100; i++) {
            assertTriggered(fsm, String.valueOf(i), EVENT, TARGET);
        }

        //Assert 'a' ~ 'z' should all be remained current after triggered
        for (String character : lowerCaseCharacters) {
            assertRemainsCurrentState(fsm, character, EVENT);
        }
    }

    /**
     * In this complex case, we aim to apply the rule:
     * The States are 'start', '97', '98', '99', ..., '122', 'target', 'a', 'b', ... 'z', 'end'
     * and the transitions are:
     * (1) All the numbers should be triggered to its corresponding ascii character on the event 'ascii'
     * e.g. '97' should be triggered to 'a' on 'ascii'
     * (2) All the characters should be triggered to its corresponding ascii number on the event 'ascii'
     * e.g. 'a' should be triggered to '97' on 'ascii'
     * (3) All the numbers should be triggered to 'target' on the event 'shoot'
     * (4) All the numbers should be triggered to its next number on the event 'next',
     * but '122' should remain current, e.g. '65' should be triggered to '66' on 'next'
     * (5) All the characters should be triggered to its next character on the event 'next',
     * but 'z' should remain current, e.g. 'a' should be triggered to 'b' on 'next'.
     * (6) All the states should be triggered to 'end' on the event 'finalize'
     * (7) The initial state is 'start'
     */
    @Test
    public void fsmComplexCaseTest(){
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();

        fsm.addState(START);
        fsm.setCurrentState(START);  // Rule (7)

        fsm.addState(END);
        fsm.addState(TARGET);

        // Add states '122', '121', ..., '97'
        fsm.addState("122");
        for (int i = 121; i >= 97; i--) {
            fsm.addState(String.valueOf(i));
            fsm.addTransition(String.valueOf(i), NEXT, String.valueOf(i+1));  // Rule (4)
        }

        // Rule (3), at this line, we haven't added characters,
        // so the characters (added below) should not have such transition.
        // Also notice, 'start' and 'end' should also not have such transition according to Rule (3),
        // but we have added them into FSM, thus we have to exclude them.
        fsm.addTransitionFromAllStates(SHOOT, TARGET, START, END);


        String[] lowerCaseCharacters = StringUtility.lowerCaseCharacters();

        for (String lowerCaseCharacter : lowerCaseCharacters) {
            fsm.addState(lowerCaseCharacter);
            String asciiCode = String.valueOf(lowerCaseCharacter.codePointAt(0));
            fsm.addTransition(asciiCode, ASCII, lowerCaseCharacter); // Rule (1)
            fsm.addTransition(lowerCaseCharacter, ASCII, asciiCode); // Rule (2)
        }

        // Rule (5)
        for (int i = 0; i < lowerCaseCharacters.length - 1; i++)
            fsm.addTransition(lowerCaseCharacters[i], NEXT, lowerCaseCharacters[i+1]);

        //Rule (6)
        fsm.addTransitionFromAllStates(FINALIZE, END);

        // Testing Started

        assertEquals(START, fsm.getCurrentState());  // Test (7)

        for (int i = 0; i < lowerCaseCharacters.length - 1; i ++) {
            String character = lowerCaseCharacters[i];
            int asciiNum = i + 97;
            String ascii = String.valueOf(asciiNum);
            assertTriggered(fsm, character, ASCII, ascii);  // Test (2)
            assertTriggered(fsm, ascii, ASCII, character); // Test (1)

            assertTriggered(fsm, character, NEXT, lowerCaseCharacters[i+1]); // Test (5)
            assertTriggered(fsm, ascii, NEXT, String.valueOf(asciiNum+1)); // Test (4)
        }

        // Test (3)
        assertRemainsCurrentState(fsm, START, SHOOT);
        assertRemainsCurrentState(fsm, TARGET, SHOOT);
        assertRemainsCurrentState(fsm, END, SHOOT);
        for (String lowerCaseCharacter : lowerCaseCharacters)
            assertRemainsCurrentState(fsm, lowerCaseCharacter, SHOOT);

        for (int i = 97; i <= 122; i++)
            assertTriggered(fsm, String.valueOf(i), SHOOT, TARGET);


        // Test (6)
        assertTriggered(fsm, START, FINALIZE, END);
        assertTriggered(fsm, TARGET, FINALIZE, END);
        assertTriggered(fsm, END, FINALIZE, END);

        for (int i = 97; i <= 122; i++)
            assertTriggered(fsm, String.valueOf(i), FINALIZE, END);

        for (String lowerCaseCharacter : lowerCaseCharacters) {
            assertTriggered(fsm, lowerCaseCharacter, FINALIZE, END);
        }
        System.out.println(fsm.toString());
    }

    private void assertTriggered(FiniteStateMachine<String> fsm, String fromState, String event, String toState){
        fsm.setCurrentState(fromState);
        assertEquals(toState, fsm.trigger(event));
        assertEquals(toState, fsm.getCurrentState());
    }

    private void assertRemainsCurrentState(FiniteStateMachine<String> fsm, String state, String event){
        fsm.setCurrentState(state);
        assertEquals(state, fsm.trigger(event));
        assertEquals(state, fsm.getCurrentState());
    }

    @Test
    public void testEmptyFsmEquality() {
        assertEquals(new FiniteStateMachine(), new FiniteStateMachine());
    }

    @Test
    public void testFsmEquality() {
        FiniteStateMachine<String> fsm1 = createBasicFsm();
        FiniteStateMachine<String> fsm2 = createBasicFsm();
        assertEquals(fsm1, fsm2);

        fsm2.addState("FSM2 has been changed, they should no longer be equal.");
        assertNotEquals(fsm1, fsm2);
    }

    @Test
    public void testFsmHashcode() {
        FiniteStateMachine<String> fsm1 = createBasicFsm();
        FiniteStateMachine<String> fsm2 = createBasicFsm();
        assertEquals(fsm1.hashCode(), fsm2.hashCode());

        fsm2.addState("FSM2 has been changed, they should no longer be equal.");
        assertNotEquals(fsm1.hashCode(), fsm2.hashCode());
    }

    @Test
    public void testGetAllStates() {
        FiniteStateMachine<String> fsm = new FiniteStateMachine<>();
        String[] states = new String[]{"a", "123", "test", "", "-\\-"};
        for (String state : states) {
            fsm.addState(state);
        }

        Set<String> statesFromFsm = new HashSet<>(fsm.getStates());
        for (String state : states) {
            assertTrue(statesFromFsm.contains(state));
        }
    }

    @Test
    public void testShallowClone() {
        FiniteStateMachine<String> fsm = createBasicFsm();
        FiniteStateMachine<String> clone = fsm.clone();

        assertNotSameButEquals(fsm, clone);
    }
}
