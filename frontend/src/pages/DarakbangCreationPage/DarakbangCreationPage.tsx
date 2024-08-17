import * as S from './DarakbangCreationPage.style';

import { ChangeEvent, useState } from 'react';

import Button from '@_components/Button/Button';
import ErrorControlledInput from '@_components/ErrorControlledInput/ErrorControlledInput';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import POLICES from '@_constants/poclies';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import SolidArrow from '@_components/Icons/SolidArrow';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function DarakbangCreationPage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [darakbangName, setDarakbangName] = useState('');
  const [nickname, setNickname] = useState('');
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
          <h1 css={theme.typography.h5}>다락방 만들기</h1>
        </SelectLayout.Header.Center>
      </SelectLayout.Header>
      <SelectLayout.ContentContainer>
        <div css={S.labeledInput}>
          <HighlightSpan>
            {'다락방의 '}
            <HighlightSpan.Highlight>이름</HighlightSpan.Highlight>은
            무엇인가요?
          </HighlightSpan>

          <ErrorControlledInput
            errorMessage=""
            placeholder="다락방의 이름을 입력해주세요"
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              setDarakbangName(e.target.value);
            }}
          />
        </div>
        <div css={S.labeledInput}>
          <HighlightSpan>
            <HighlightSpan.Highlight>닉네임</HighlightSpan.Highlight>을
            입력해주세요{'\n최대 '}
            <HighlightSpan.Highlight>{`${POLICES.maxNicknameLength}글자`}</HighlightSpan.Highlight>
            까지 가능해요
          </HighlightSpan>
          <ErrorControlledInput
            errorMessage=""
            placeholder="닉네임을 입력해주세요"
            maxLength={POLICES.maxNicknameLength}
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              setNickname(e.target.value);
            }}
          />
        </div>
      </SelectLayout.ContentContainer>
      <SelectLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          primary
          disabled={darakbangName === '' || nickname === ''}
        >
          다락방 만들기
        </Button>
      </SelectLayout.BottomButtonWrapper>
    </SelectLayout>
  );
}
