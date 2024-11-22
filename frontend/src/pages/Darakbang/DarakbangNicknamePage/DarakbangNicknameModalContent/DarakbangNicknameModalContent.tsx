import * as S from './DarakbangNicknameModalContent.style';

import Button from '@_components/Button/Button';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import { useTheme } from '@emotion/react';

interface ModalContentProps {
  nickname: string;
  onCancel: () => void;
  onConfirm: () => void;
}

export default function DarakbangNicknameModalContent(
  props: ModalContentProps,
) {
  const { nickname, onCancel, onConfirm } = props;

  const theme = useTheme();
  return (
    <div css={S.content({ theme })}>
      <HighlightSpan font={theme.typography.s1}>
        <HighlightSpan.Highlight>{nickname}</HighlightSpan.Highlight>
        {
          '을 선택하셨습니다.\n\n한 번 선택한 닉네임은 바꿀 수 없습니다.\n진행하시겠습니까?'
        }
      </HighlightSpan>
      <div css={S.buttonContainer}>
        <div css={S.buttonWrapper}>
          <Button
            shape="bar"
            reversePrimary
            onClick={onCancel}
            font={theme.typography.Medium}
          >
            취소
          </Button>
        </div>
        <div css={S.buttonWrapper}>
          <Button
            shape="bar"
            primary
            onClick={onConfirm}
            font={theme.typography.Medium}
          >
            선택하기
          </Button>
        </div>
      </div>
    </div>
  );
}
