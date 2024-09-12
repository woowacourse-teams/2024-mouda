import * as S from './DarakbangMembersPage.style';

import MemberCard from '@_components/MemberCard/MemberCard';
import SolidArrow from '@_components/Icons/SolidArrow';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import useDarakbangMembers from '@_hooks/queries/useDarakbangMembers';
import { useNavigate } from 'react-router-dom';

export default function DarakbangMembersPage() {
  const navigate = useNavigate();

  const { members, isLoading } = useDarakbangMembers();
  return (
    <>
      <StickyTriSectionHeader>
        <TriSectionHeader.Left>
          <SolidArrow direction="left" onClick={() => navigate(-1)} />
        </TriSectionHeader.Left>
        <TriSectionHeader.Center>멤버목록</TriSectionHeader.Center>
      </StickyTriSectionHeader>
      {isLoading && <>loading...</>}
      <div css={S.members}>
        {members?.map(({ nickname, memberId, profile }) => (
          <MemberCard key={memberId} name={nickname} imageUrl={profile} />
        ))}
      </div>
    </>
  );
}
