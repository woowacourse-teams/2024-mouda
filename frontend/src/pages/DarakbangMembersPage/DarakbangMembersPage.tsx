import * as S from './DarakbangMembersPage.style';

import MemberCard from '@_components/MemberCard/MemberCard';
import SolidArrow from '@_components/Icons/SolidArrow';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import { useNavigate } from 'react-router-dom';

export default function DarakbangMembersPage() {
  const list = new Array(100).fill(null).map((_, i) => {
    return { name: '소파' + i };
  });
  const navigate = useNavigate();
  return (
    <>
      <StickyTriSectionHeader>
        <TriSectionHeader.Left>
          <SolidArrow direction="left" onClick={() => navigate(-1)} />
        </TriSectionHeader.Left>
        <TriSectionHeader.Center>멤버목록</TriSectionHeader.Center>
      </StickyTriSectionHeader>
      <div css={S.members}>
        {list.map(({ name }) => (
          <MemberCard key={name} name={name} />
        ))}
      </div>
    </>
  );
}
