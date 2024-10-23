import { ChangeEvent, useState } from 'react';

import Button from '@_components/Button/Button';
import ErrorControlledInput from '@_components/ErrorControlledInput/ErrorControlledInput';
import POLICES from '@_constants/poclies';
import ROUTES from '@_constants/routes';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';

export default function DarakbangEntrancePage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [inviteCode, setInviteCode] = useState('');
  return (
    <SelectLayout>
      <SelectLayout.Header>
        <SelectLayout.Header.Left>
          <BackArrowButton
            onClick={() => {
              navigate(-1);
            }}
          />
        </SelectLayout.Header.Left>
        <SelectLayout.Header.Center>
          <h1 css={theme.typography.s1}>다락방 참여코드 입력</h1>
        </SelectLayout.Header.Center>
      </SelectLayout.Header>
      <SelectLayout.ContentContainer>
        <ErrorControlledInput
          errorMessage=""
          placeholder="참여코드를 입력하세요"
          maxLength={POLICES.entranceCodeLength}
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            setInviteCode(e.target.value.toUpperCase());
          }}
          style={{ textTransform: 'uppercase' }}
        />
      </SelectLayout.ContentContainer>
      <SelectLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          primary
          disabled={inviteCode.length !== POLICES.entranceCodeLength}
          onClick={() => {
            navigate(`${ROUTES.darakbangInvitationRoute}?code=${inviteCode}`);
          }}
        >
          다락방 참여하기
        </Button>
      </SelectLayout.BottomButtonWrapper>
    </SelectLayout>
  );
}
