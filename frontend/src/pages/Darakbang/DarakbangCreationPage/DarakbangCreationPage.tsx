import * as S from './DarakbangCreationPage.style';

import { ChangeEvent, useState } from 'react';

import Button from '@_components/Button/Button';
import DarakbangCreationModalContent from './DarakbangCreationModalContent/DarakbangCreationModalContent';
import ErrorControlledInput from '@_components/ErrorControlledInput/ErrorControlledInput';
import GET_ROUTES from '@_common/getRoutes';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import Modal from '@_components/Modal/Modal';
import POLICES from '@_constants/poclies';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import { setLastDarakbangId } from '@_common/lastDarakbangManager';
import useCreateDarakbang from '@_hooks/mutaions/useCreateDarakbang';
import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';

export default function DarakbangCreationPage() {
  const theme = useTheme();
  const navigate = useNavigate();
  const { mutate: createDarakbang } = useCreateDarakbang(
    (darakbangId: number) => {
      setLastDarakbangId(darakbangId);
      navigate(GET_ROUTES.nowDarakbang.darakbangLanding());
    },
  );

  const [darakbangName, setDarakbangName] = useState('');
  const [nickname, setNickname] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

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
          <h1 css={theme.typography.h5}>다락방 만들기</h1>
        </SelectLayout.Header.Center>
      </SelectLayout.Header>
      <SelectLayout.ContentContainer>
        <div css={S.labeledInput}>
          <HighlightSpan ariaLabel="다락방의 이름은 무엇인가요?">
            다락방의 <HighlightSpan.Highlight>이름</HighlightSpan.Highlight>은
            무엇인가요?
          </HighlightSpan>

          <ErrorControlledInput
            errorMessage=""
            placeholder="다락방의 이름을 입력해주세요."
            onChange={(e: ChangeEvent<HTMLInputElement>) => {
              setDarakbangName(e.target.value);
            }}
            maxLength={POLICES.maxDarakbangName}
          />
        </div>
        <div css={S.labeledInput}>
          <HighlightSpan ariaLabel="닉네임을 입력해주세요.최대 12글자까지 가능해요.">
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
          onClick={() => setIsModalOpen(true)}
        >
          다락방 만들기
        </Button>
      </SelectLayout.BottomButtonWrapper>
      {isModalOpen && (
        <Modal onClose={() => setIsModalOpen(false)}>
          <DarakbangCreationModalContent
            darakbangName={darakbangName}
            nickname={nickname}
            onCancel={() => setIsModalOpen(false)}
            onConfirm={() => {
              createDarakbang({ nickname, name: darakbangName });
            }}
          />
        </Modal>
      )}
    </SelectLayout>
  );
}
