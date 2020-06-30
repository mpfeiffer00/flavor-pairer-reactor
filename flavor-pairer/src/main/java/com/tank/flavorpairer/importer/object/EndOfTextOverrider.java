package com.tank.flavorpairer.importer.object;

public interface EndOfTextOverrider {
	/**
	 * Overrides general parsing of an end of text signal.
	 * 
	 * @param endOfTextCriteria      The {@link EndOfTextCriteria} that contains
	 *                               general ingredient processing information.
	 * @param endOfTextStateCriteria The {@link EndOfTextStateCriteria} that
	 *                               contains state necessary to pass between
	 *                               subsequent processes.
	 * @return True if one of the override exceptions was executed, false otherwise.
	 */
	boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria);
}
