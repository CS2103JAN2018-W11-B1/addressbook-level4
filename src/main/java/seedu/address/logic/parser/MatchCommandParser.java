package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] indices = trimmedArgs.split("\\s+");
        Index indexA = null;
        Index indexB = null;
        try {
            indexA = ParserUtil.parseIndex(indices[0]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    new String(MESSAGE_INVALID_COMMAND_FORMAT  + MatchCommand.MESSAGE_USAGE));
        }

        try {
            indexB = ParserUtil.parseIndex(indices[1]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    new String(MESSAGE_INVALID_COMMAND_FORMAT  + MatchCommand.MESSAGE_USAGE));
        }

        return new MatchCommand(indexA, indexB);
    }
}