import * as S from './DarakbangInvitationPage.style';

import Button from '@_components/Button/Button';
import CompleteBottomWrapper from '@_layouts/CompleteLayout/CompleteBottomWrapper/CompleteBottomWrapper';
import ROUTES from '@_constants/routes';
import SolidArrow from '@_components/Icons/SolidArrow';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function DarakbangInvitationPage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const code = 'NUFSOU9';
  const darakbangName = '우아한테크코스';
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
          <Button
            shape="bar"
            primary
            onClick={async () => {
              const url = `${process.env.BASE_URL}${ROUTES.darakbangInvitationRoute}?code=${code}`;
              if (navigator.share) {
                await navigator.share({
                  title: `${darakbangName} 다락방으로의 초대`,
                  text: `${darakbangName}으로 초대되었어요~!\n모우다에서 함께 모여보세요~`,
                  url,
                });
                return;
              }

              if (document.location.protocol === 'https:') {
                await navigator.clipboard.writeText(
                  `${process.env.BASE_URL}${1}?code=${1}`,
                );
                return;
              }
              alert('http를 통해서는 복사할 수 없습니다!');
            }}
          >
            초대링크 공유하기
          </Button>
        </CompleteBottomWrapper>
      </div>
    </div>
  );
}
