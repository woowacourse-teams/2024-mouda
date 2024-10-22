import * as S from './DarakbangSelectPage.style';

import GET_ROUTES from '@_common/getRoutes';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import MissingFallback from '@_components/Fallback/MissingFallback/MissingFallback';
import ROUTES from '@_constants/routes';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import SolidArrow from '@_components/Icons/SolidArrow';
import { common } from '@_common/common.style';
import { setLastDarakbangId } from '@_common/lastDarakbangManager';
import useMyDarakbangs from '@_hooks/queries/useMyDarakbang';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import SelectBar from '../components/SelectBar/SelectBar';

export default function DarakbangSelectPage() {
  const theme = useTheme();
  const navigate = useNavigate();

  const { myDarakbangs, isLoading } = useMyDarakbangs();
  return (
    <SelectLayout>
      <SelectLayout.Header>
        <SelectLayout.Header.Left>
          <SolidArrow
            direction="left"
            onClick={() => {
              navigate(-1);
            }}
          />
        </SelectLayout.Header.Left>
        <SelectLayout.Header.Center>
          <h1 css={theme.typography.h5}>다락방 선택</h1>
        </SelectLayout.Header.Center>
      </SelectLayout.Header>
      <SelectLayout.ContentContainer>
        <HighlightSpan>
          {'어느 '}
          <HighlightSpan.Highlight>다락방</HighlightSpan.Highlight>에
          들어갈까요?
        </HighlightSpan>
        {isLoading && <>loading...</>}
        {myDarakbangs &&
          myDarakbangs?.length > 0 &&
          myDarakbangs.map(({ darakbangId, name }) => (
            <SelectBar
              key={darakbangId}
              onClick={() => {
                setLastDarakbangId(darakbangId);
                navigate(GET_ROUTES.nowDarakbang.main());
              }}
            >
              {name}
            </SelectBar>
          ))}

        {myDarakbangs && myDarakbangs.length === 0 && (
          <div css={S.fallbackContainer}>
            <MissingFallback text="참여 중인 다락방이 없어요" />
          </div>
        )}
        <div
          css={[S.bottom({ theme }), common.cursorPointer]}
          onClick={() => navigate(ROUTES.darakbangEntrance)}
        >
          참여코드로 다락방 들어가기
        </div>
      </SelectLayout.ContentContainer>
    </SelectLayout>
  );
}
