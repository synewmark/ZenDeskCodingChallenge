package model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Ticket {
	public long assignee_id;
	public long[] collaborator_ids;
	public LocalDateTime created_at;
	public CustomFields[] custom_fields;
	public String description;
	public LocalDateTime due_at;
	public String external_id;
	public long[] followers_ids;
	public long group_id;
	public boolean has_incidents;
	public long id;
	public long organization_id;
	public PRIORITY priority;
	public long problem_id;
	public String raw_subject;
	public String recipient;
	public long requester_id;
	public SatisfactionRating satisfaction_rating;
	public long[] sharing_agreement_ids;
	public STATUS status;
	public String subject;
	public long submitter_id;
	public String[] tags;
	public long ticket_form_id;
	public TYPE type;
	public LocalDateTime updated_at;
	public LocalDateTime updated_stamp;
	public URL url;
	public Via via;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(collaborator_ids);
		result = prime * result + Arrays.hashCode(custom_fields);
		result = prime * result + Arrays.hashCode(followers_ids);
		result = prime * result + Arrays.hashCode(sharing_agreement_ids);
		result = prime * result + Arrays.hashCode(tags);
		result = prime * result + Objects.hash(assignee_id, created_at, description, due_at, external_id, group_id,
				has_incidents, id, organization_id, priority, problem_id, raw_subject, recipient, requester_id,
				satisfaction_rating, status, subject, submitter_id, ticket_form_id, type, updated_at, updated_stamp,
				url, via);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return assignee_id == other.assignee_id && Arrays.equals(collaborator_ids, other.collaborator_ids)
				&& Objects.equals(created_at, other.created_at) && Arrays.equals(custom_fields, other.custom_fields)
				&& Objects.equals(description, other.description) && Objects.equals(due_at, other.due_at)
				&& Objects.equals(external_id, other.external_id) && Arrays.equals(followers_ids, other.followers_ids)
				&& group_id == other.group_id && has_incidents == other.has_incidents && id == other.id
				&& organization_id == other.organization_id && priority == other.priority
				&& problem_id == other.problem_id && Objects.equals(raw_subject, other.raw_subject)
				&& Objects.equals(recipient, other.recipient) && requester_id == other.requester_id
				&& Objects.equals(satisfaction_rating, other.satisfaction_rating)
				&& Arrays.equals(sharing_agreement_ids, other.sharing_agreement_ids) && status == other.status
				&& Objects.equals(subject, other.subject) && submitter_id == other.submitter_id
				&& Arrays.equals(tags, other.tags) && ticket_form_id == other.ticket_form_id && type == other.type
				&& Objects.equals(updated_at, other.updated_at) && Objects.equals(updated_stamp, other.updated_stamp)
				&& Objects.equals(url, other.url) && Objects.equals(via, other.via);
	}

	@Override
	public String toString() {
		return "assignee_id: " + assignee_id + "\ncollaborator_ids: " + Arrays.toString(collaborator_ids)
				+ "\ncreated_at: " + created_at + "\ncustom_fields: " + Arrays.toString(custom_fields)
				+ "\ndescription: " + description + "\ndue_at: " + due_at + "\nexternal_id: " + external_id
				+ "\nfollowers_ids: " + Arrays.toString(followers_ids) + "\ngroup_id: " + group_id + "\nhas_incidents: "
				+ has_incidents + "\nid: " + id + "\norganization_id: " + organization_id + "\npriority: " + priority
				+ "\nproblem_id: " + problem_id + "\nraw_subject: " + raw_subject + "\nrecipient: " + recipient
				+ "\nrequester_id: " + requester_id + "\nsatisfaction_rating: " + satisfaction_rating
				+ "\nsharing_agreement_ids: " + Arrays.toString(sharing_agreement_ids) + "\nstatus: " + status
				+ "\nsubject: " + subject + "\nsubmitter_id: " + submitter_id + "\ntags: " + Arrays.toString(tags)
				+ "\nticket_form_id: " + ticket_form_id + "\ntype: " + type + "\nupdated_at: " + updated_at
				+ "\nupdated_stamp: " + updated_stamp + "\nurl: " + url + "\nvia: " + via;
	}

	public String toSimplifiedString() {
		return "Subject: " + subject + "\t Priority: " + priority + "\t Assignee: " + assignee_id + '\n'
				+ "Collaborators: " + Arrays.toString(collaborator_ids) + "\t Created: " + created_at;
	}

	static class CustomFields {
		long id;
		String value;

		@Override
		public int hashCode() {
			return Objects.hash(id, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CustomFields other = (CustomFields) obj;
			return id == other.id && Objects.equals(value, other.value);
		}

		@Override
		public String toString() {
			return "CustomFields [id=" + id + ", value=" + value + "]";
		}
	}

	static class SatisfactionRating {
		String comment;
		long id;
		String score;

		@Override
		public int hashCode() {
			return Objects.hash(comment, id, score);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SatisfactionRating other = (SatisfactionRating) obj;
			return Objects.equals(comment, other.comment) && id == other.id && Objects.equals(score, other.score);
		}

		@Override
		public String toString() {
			return "SatisfactionRating [comment=" + comment + ", id=" + id + ", score=" + score + "]";
		}

	}

	static class Via {
		String channel;

		@Override
		public int hashCode() {
			return Objects.hash(channel);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Via other = (Via) obj;
			return Objects.equals(channel, other.channel);
		}

		@Override
		public String toString() {
			return "Via [channel=" + channel + "]";
		}

	}

	enum PRIORITY {
		LOW, NORMAL, HIGH, URGENT
	}

	enum STATUS {
		CLOSED, SOLVED, HOLD, PENDING, OPEN, NEW
	}

	enum TYPE {
		TASK, QUESTION, INCIDENT, PROBLEM
	}

}
