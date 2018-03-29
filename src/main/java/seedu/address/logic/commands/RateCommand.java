package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a remark to person to the address book.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";
    public static final String COMMAND_WORD_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to person identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK;

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Added Remark to %1$s: " + "%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private Remark newRemark;

    private Person personToEdit;
    private Person editedPerson;

    public RateCommand(Index targetIndex, Remark newRemark) {
        requireNonNull(targetIndex);
        requireNonNull(newRemark);

        this.targetIndex = targetIndex;
        this.newRemark = newRemark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS,
                                editedPerson.getName(), editedPerson.getRemark()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createPersonWithNewRemark(personToEdit, newRemark);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}.
     */
    private static Person createPersonWithNewRemark(Person personToEdit, Remark newRemark) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Price price = personToEdit.getPrice();
        Subject subject = personToEdit.getSubject();
        Level level = personToEdit.getLevel();
        Status status = personToEdit.getStatus();
        Role role = personToEdit.getRole();

        Set<Tag> updatedTags = personToEdit.getTags();

        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //clean out old person's attribute tags, then add the new ones

        //ignore if attribute is empty (not entered yet by user)
        if (!personToEdit.getPrice().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getPrice().toString(), Tag.AllTagTypes.PRICE));
        }
        if (!personToEdit.getLevel().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getLevel().toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!personToEdit.getSubject().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getSubject().toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!personToEdit.getStatus().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getStatus().toString(), Tag.AllTagTypes.STATUS));
        }
        if (!personToEdit.getRole().toString().equals("")) {
            attributeTags.add(new Tag(personToEdit.getRole().toString(), Tag.AllTagTypes.ROLE));
        }

        return new Person(name, phone, email, address, price, subject, level, status, role, attributeTags, newRemark);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RateCommand // instanceof handles nulls
                && this.targetIndex.equals(((RateCommand) other).targetIndex)
                && this.newRemark.equals(((RateCommand) other).newRemark));
    }
}
