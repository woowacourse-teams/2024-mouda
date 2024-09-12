import * as S from './DarakbangCreationModalContent.style';

import Button from '@_components/Button/Button';
import HighlightSpan from '@_components/HighlightSpan/HighlightSpan';
import { useTheme } from '@emotion/react';

interface ModalContentProps {
  darakbangName: string;
  nickname: string;
  onCancel: () => void;
  onConfirm: () => void;
}

export default function DarakbangCreationModalContent(
  props: ModalContentProps,
) {
  const { darakbangName, nickname, onCancel, onConfirm } = props;

  const theme = useTheme();
  return (
    <div css={S.content({ theme })}>
      <HighlightSpan font={theme.typography.s1}>
        {'다락방 이름으로 '}
        <HighlightSpan.Highlight>{darakbangName}</HighlightSpan.Highlight>
        {',\n닉네임으로 '}
        <HighlightSpan.Highlight>{nickname}</HighlightSpan.Highlight>
        {
          '을(를) 선택하셨습니다.\n\n한 번 선택한 다락방 이름과 닉네임은 바꿀 수 없습니다.\n진행하시겠습니까?'
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
