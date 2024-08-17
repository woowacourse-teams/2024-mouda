import * as S from './DarakbangSelectPage.style';

import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import SelectBar from '@_components/SelectBar/SelectBar';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import SolidArrow from '@_components/Icons/SolidArrow';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function DarakbangSelectPage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const darakbangs: [] = [];
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
        {darakbangs?.length > 0 &&
          darakbangs.map((name) => <SelectBar key={name}>{name}</SelectBar>)}
        {(!darakbangs || darakbangs.length === 0) && (
          <div css={S.fallbackContainer}>
            <MissingFallback text="참여 중인 다락방이 없어요" />
          </div>
        )}
        <div css={S.bottom({ theme })}>참여코드로 다락방 들어가기</div>
      </SelectLayout.ContentContainer>
    </SelectLayout>
  );
}
