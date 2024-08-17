import * as S from './DarakbangInvitationPage.style';

import Button from '@_components/Button/Button';
import CompleteBottomWrapper from '@_layouts/CompleteLayout/CompleteBottomWrapper/CompleteBottomWrapper';
import SolidArrow from '@_components/Icons/SolidArrow';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function DarakbangInvitationPage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const code = 'NUFSOU9';
  return (
    <div css={S.layout}>
      <StickyTriSectionHeader>
        <StickyTriSectionHeader.Left>
          <SolidArrow direction="left" onClick={() => navigate(-1)} />
        </StickyTriSectionHeader.Left>
        <StickyTriSectionHeader.Center>멤버 초대</StickyTriSectionHeader.Center>
      </StickyTriSectionHeader>
      <div css={S.container}>
        <div css={theme.typography.s1}>다락방 초대코드</div>

        <div css={S.code({ theme })}>{code}</div>

        <CompleteBottomWrapper>
          <Button shape="bar" primary>
            초대링크 복사하기
          </Button>
        </CompleteBottomWrapper>
      </div>
    </div>
  );
}
