package aventurian;

import org.junit.Before;
import org.junit.Test;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.*;
import static aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE.*;
import static org.junit.Assert.*;

public class SecondaryAttributesTest {

    private SecondaryAttributes toTest;

    @Before
    public void setUp() throws Exception {
        this.toTest = new SecondaryAttributes();
    }

    @Test
    public void updateValuesMinimum() throws Exception {
        PrimaryAttributes attributes = new PrimaryAttributes();
        toTest.updateValues(attributes);
        assertHitPointsMinimum();
        assertAstralPointsMinimum();
        assertKarmalPointsMinium();
        assertMagicResistanceMinimum();
        assertExhaustionThreshholdMinimum();
        assertWoundThreshholdMinimum();
        assertInitiativeValueMinimum();
        assertAttackValueMinimum();
        assertDefendValueMinimum();
        assertRangedValueMinimum();
    }

    @Test
    public void updateValuesMaximum() throws Exception {
        PrimaryAttributes attributes = getMaximumPrimaryAttributes();
        toTest.updateValues(attributes);
        assertHitPointsMaximum();
        assertAstralPointsMaximum();
        assertKarmalPointsMaximum();
        assertMagicResistanceMaximum();
        assertExhaustionThreshholdMaximum();
        assertWoundThreshholdMaximum();
        assertInitiativeValueMaximum();
        assertAttackValueMaximum();
        assertDefendValueMaximum();
        assertRangedValueMaximum();
    }

    private PrimaryAttributes getMaximumPrimaryAttributes() {
        PrimaryAttributes attributes = new PrimaryAttributes();
        for (int i = 8; i < 14; i++) {
            attributes.increase(COURAGE);
            attributes.increase(INTELLIGENCE);
            attributes.increase(INTUITION);
            attributes.increase(CHARISMA);
            attributes.increase(DEXTERITY);
            attributes.increase(AGILITY);
            attributes.increase(CONSTITUTION);
            attributes.increase(STRENGTH);
        }
        return attributes;
    }

    private void assertHitPointsMinimum() {
        assertEquals(12, toTest.getValueOf(HITPOINTS));
    }

    private void assertAstralPointsMinimum() {
        assertEquals(12, toTest.getValueOf(ASTRALPOINTS));
    }

    private void assertKarmalPointsMinium() {
        assertEquals(0, toTest.getValueOf(KARMALPOINTS));
    }

    private void assertMagicResistanceMinimum() {
        assertEquals(5, toTest.getValueOf(MAGICRESISTANCE));
    }

    private void assertExhaustionThreshholdMinimum() {
        assertEquals(4, toTest.getValueOf(EXHAUSTIONTHRESHHOLD));
    }

    private void assertWoundThreshholdMinimum() {
        assertEquals(4, toTest.getValueOf(WOUNDTHRESHHOLD));
    }

    private void assertInitiativeValueMinimum() {
        assertEquals(6, toTest.getValueOf(INITIATIVEVALUE));
    }

    private void assertAttackValueMinimum() {
        assertEquals(5, toTest.getValueOf(ATTACKVALUE));
    }

    private void assertDefendValueMinimum() {
        assertEquals(5, toTest.getValueOf(DEFENDVALUE));
    }

    private void assertRangedValueMinimum() {
        assertEquals(5, toTest.getValueOf(RANGEDVALUE));
    }


    private void assertHitPointsMaximum() {
        assertEquals(21, toTest.getValueOf(HITPOINTS));
    }

    private void assertAstralPointsMaximum() {
        assertEquals(21, toTest.getValueOf(ASTRALPOINTS));
    }

    private void assertKarmalPointsMaximum() {
        assertEquals(0, toTest.getValueOf(KARMALPOINTS));
    }

    private void assertMagicResistanceMaximum() {
        assertEquals(8, toTest.getValueOf(MAGICRESISTANCE));
    }

    private void assertExhaustionThreshholdMaximum() {
        assertEquals(7, toTest.getValueOf(EXHAUSTIONTHRESHHOLD));
    }

    private void assertWoundThreshholdMaximum() {
        assertEquals(7, toTest.getValueOf(WOUNDTHRESHHOLD));
    }

    private void assertInitiativeValueMaximum() {
        assertEquals(11, toTest.getValueOf(INITIATIVEVALUE));
    }

    private void assertAttackValueMaximum() {
        assertEquals(8, toTest.getValueOf(ATTACKVALUE));
    }

    private void assertDefendValueMaximum() {
        assertEquals(8, toTest.getValueOf(DEFENDVALUE));
    }

    private void assertRangedValueMaximum() {
        assertEquals(8, toTest.getValueOf(RANGEDVALUE));
    }

    @Test
    public void getValueOf() throws Exception {
        int actual = toTest.getValueOf(HITPOINTS);
        int expected = 0;
        assertEquals(expected, actual);
    }

}