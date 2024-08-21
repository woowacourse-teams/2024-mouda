import { ChangeEvent, useState } from 'react';
import { getInviteCode, removeInviteCode } from '@_common/inviteCodeManager';

import Button from '@_components/Button/Button';
import DarakbangNicknameModalContent from './DarakbangNicknameModalContent/DarakbangNicknameModalContent';
import ErrorControlledInput from '@_components/ErrorControlledInput/ErrorControlledInput';
import GET_ROUTES from '@_common/getRoutes';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import Modal from '@_components/Modal/Modal';
import POLICES from '@_constants/poclies';
import SolidArrow from '@_components/Icons/SolidArrow';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import StretchContentLayout from '@_layouts/StretchContentLayout/StretchContentLayout';
import useDarakbangNameByCode from '@_hooks/queries/useDarakbangNameByCode';
import useEnterDarakbang from '@_hooks/mutaions/useEnterDarakbang';
import { useNavigate } from 'react-router-dom';

export default function DarakbangNicknamePage() {
  const navigate = useNavigate();

  const code = getInviteCode() || '';

  const { darakbangName = '' } = useDarakbangNameByCode(code);
  const [nickname, setNickName] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const { mutate: enterDarakbang } = useEnterDarakbang({
    onSuccess: () => {
      removeInviteCode();
      navigate(GET_ROUTES.nowDarakbang.darakbangLanding());
    },
    onError: (string: string) => {
      setIsModalOpen(false);
      setErrorMessage(string);
    },
  });

  return (
    <StretchContentLayout>
      <StickyTriSectionHeader>
        <StickyTriSectionHeader.Left>
          <SolidArrow
            direction="left"
            onClick={() => {
              removeInviteCode();
              navigate(-1);
            }}
          />
        </StickyTriSectionHeader.Left>
        <StickyTriSectionHeader.Center>
          {darakbangName}
        </StickyTriSectionHeader.Center>
      </StickyTriSectionHeader>
      <StretchContentLayout.ContentContainer>
        <HighlightSpan>
          <HighlightSpan.Highlight>{darakbangName}</HighlightSpan.Highlight>
          {' 다락방에 오신 것을 환영해요.\n\n'}
          <HighlightSpan.Highlight>닉네임</HighlightSpan.Highlight>
          {'을 입력해주세요.\n최대 '}
          <HighlightSpan.Highlight>{`${POLICES.maxNicknameLength}글자`}</HighlightSpan.Highlight>
          까지 가능해요.
        </HighlightSpan>

        <ErrorControlledInput
          errorMessage={errorMessage}
          maxLength={POLICES.maxNicknameLength}
          placeholder="닉네임을 입력해주세요"
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            setNickName(e.target.value);
          }}
        />
      </StretchContentLayout.ContentContainer>
      <StretchContentLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          primary
          disabled={!nickname}
          onClick={() => setIsModalOpen(true)}
        >
          등록하기
        </Button>
      </StretchContentLayout.BottomButtonWrapper>
      {isModalOpen && (
        <Modal onClose={() => setIsModalOpen(false)}>
          <DarakbangNicknameModalContent
            onCancel={() => setIsModalOpen(false)}
            onConfirm={() => {
              enterDarakbang({ code, nickname });
            }}
            nickname={nickname}
          />
        </Modal>
      )}
    </StretchContentLayout>
  );
}
