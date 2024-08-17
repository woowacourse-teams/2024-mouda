import { ChangeEvent, useState } from 'react';

import Button from '@_components/Button/Button';
import ErrorControlledInput from '@_components/ErrorControlledInput/ErrorControlledInput';
import POLICES from '@_constants/poclies';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import SolidArrow from '@_components/Icons/SolidArrow';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';

export default function DarakbangEntrancePage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [entranceCode, setEntranceCode] = useState('');
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
          <h1 css={theme.typography.h5}>다락방 참여코드 입력</h1>
        </SelectLayout.Header.Center>
      </SelectLayout.Header>
      <SelectLayout.ContentContainer>
        <ErrorControlledInput
          errorMessage=""
          placeholder="참여코드를 입력하세요"
          maxLength={POLICES.entranceCodeLength}
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            setEntranceCode(e.target.value.toUpperCase());
          }}
          style={{ textTransform: 'uppercase' }}
        />
      </SelectLayout.ContentContainer>
      <SelectLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          primary
          disabled={entranceCode.length !== POLICES.entranceCodeLength}
        >
          다락방 참여하기
        </Button>
      </SelectLayout.BottomButtonWrapper>
    </SelectLayout>
  );
}
