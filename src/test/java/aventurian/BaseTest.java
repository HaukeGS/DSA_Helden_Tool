package aventurian;

import org.mockito.Mock;

import database.Database;
import logging.Logger;

public class BaseTest {
	@Mock
	protected Aventurian mockedAventurian;
	@Mock
	protected Database mockedDatabase;
	@Mock
	protected Logger mockedLogger;

}
