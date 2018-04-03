package seedu.address.model.pair;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Set;

import seedu.address.model.person.Level;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author alexawangzi
/**
 * Represents a pair (one student and one tutor) in STUtor.
 */
public class Pair  {

    public final String studentName;
    public final String tutorName;
    public final String subject;
    public final String level;
    public final String price;
    public final PairHash pairHash;
    private final UniqueTagList tags;


    public Pair(String studentName, String tutorName, String subject, String level, String price,
                PairHash pairHash) {
        requireAllNonNull(studentName, tutorName, subject, level, price, pairHash);
        this.studentName = studentName;
        this.tutorName = tutorName;
        this.subject = subject;
        this.level = level;
        this.price = price;
        this.tags = new UniqueTagList();
        try {
            tags.add(new Tag(price, Tag.AllTagTypes.PRICE));
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
        }
        try {
            tags.add(new Tag(subject, Tag.AllTagTypes.SUBJECT));
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
        }
        try {
            tags.add(new Tag(level, Tag.AllTagTypes.LEVEL));
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
        }
        this.pairHash = pairHash;
    }

    public Pair(Person student, Person tutor, Subject subject, Level level, Price price) {
        this(student.getName().fullName, tutor.getName().fullName, subject.value,
                level.value, price.value, new PairHash(student, tutor, subject, level, price));
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getSubject() {
        return subject;
    }

    public String getLevel() {
        return level;
    }

    public String getPrice() {
        return price;
    }

    public String getPairName() {
        return studentName + " /w " + tutorName;
    }

    public PairHash getPairHash() {
        return pairHash;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
    */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * return a hashcode of the Pair object
     * @return
     */
    /*  public Pair hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return pairHash;
    }
    */

    /**
     * check if another object is equal to this pair
     * @param other
     * @return
    */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pair)) {
            return false;
        }


        Pair otherPair = (Pair) other;
        return otherPair.getStudentName().equals(this.getStudentName())
                && otherPair.getTutorName().equals(this.getTutorName())
                && otherPair.getSubject().equals(this.getSubject())
                & otherPair.getLevel().equals(this.getLevel())
                && otherPair.getPrice().equals(this.getPrice())
                && otherPair.getPairHash().equals(this.getPairHash());
    }


}
