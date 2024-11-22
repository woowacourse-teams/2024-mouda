import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import ROUTES from '@_constants/routes';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import SelectBar from '../components/SelectBar/SelectBar';
import { getInviteCode } from '@_common/inviteCodeManager';
import { useEffect } from 'react';

export default function DarakbangSelectOptionPage() {
  const navigate = useNavigate();
  const theme = useTheme();

  useEffect(() => {
    if (getInviteCode() !== null) {
      navigate(ROUTES.darakbangInvitationRoute);
    }
  }, [navigate]);

  return (
    <SelectLayout>
      <SelectLayout.Header>
        <SelectLayout.Header.Center>
          <h1 css={theme.typography.h5}>다락방 선택</h1>
        </SelectLayout.Header.Center>
      </SelectLayout.Header>
      <SelectLayout.ContentContainer>
        <HighlightSpan ariaLabel="다락방에 참여해보세요">
          <HighlightSpan.Highlight>다락방</HighlightSpan.Highlight>에
          참여해보세요
        </HighlightSpan>
        <SelectBar onClick={() => navigate(ROUTES.darakbangSelect)}>
          기존 다락방 참여하기
        </SelectBar>
        <SelectBar onClick={() => navigate(ROUTES.darakbangCreation)}>
          새로운 다락방 만들기
        </SelectBar>
      </SelectLayout.ContentContainer>
    </SelectLayout>
  );
}
