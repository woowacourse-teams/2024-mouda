package mouda.backend.darakbangmember.domain;

import java.util.List;

public class DarakbangMembers {
	private final List<DarakbangMember> darakbangMembers;

	public DarakbangMembers(List<DarakbangMember> darakbangMembers) {
		this.darakbangMembers = darakbangMembers;
	}

	public List<DarakbangMember> getDarakbangMembers() {
		return darakbangMembers;
	}
}
