import GET_ROUTES from '@_common/getRoutes';
import MenuItem from '@_components/MenuItem/MenuItem';
import SolidArrow from '@_components/Icons/SolidArrow';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import { useNavigate } from 'react-router-dom';

export default function DarakbangManagementPage() {
  const navigate = useNavigate();
  return (
    <>
      <TriSectionHeader>
        <TriSectionHeader.Left>
          <SolidArrow direction="left" onClick={() => navigate(-1)} />
        </TriSectionHeader.Left>
        <TriSectionHeader.Center>다락방 관리</TriSectionHeader.Center>
      </TriSectionHeader>
      <MenuItem
        description="멤버 목록"
        onClick={() => navigate(GET_ROUTES.nowDarakbang.darakbangMembers())}
      />
      <MenuItem
        description="멤버 초대"
        onClick={() => navigate(GET_ROUTES.nowDarakbang.darakbangInvitation())}
        isLastItem
      />
    </>
  );
}
