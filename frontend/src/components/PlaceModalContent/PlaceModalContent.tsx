import * as S from './PlaceModalContent.style';

import { ChangeEvent, useMemo, useState } from 'react';

import Button from '@_components/Button/Button';
import LabeledInput from '@_components/Input/MoimInput';
import { useTheme } from '@emotion/react';

interface PlaceModalContentProps {
  onCancel: () => void;
  onConfirm: (string: string) => void;
}

export default function PlaceModalContent(props: PlaceModalContentProps) {
  const { onCancel, onConfirm } = props;
  const [place, setPlace] = useState('');

  const theme = useTheme();

  const submitHandler = useMemo(() => {
    return () => {
      if (place.length < 1) return;
      onConfirm(place);
      setPlace('');
    };
  }, [place, onConfirm]);

  return (
    <div css={S.content({ theme })} onSubmit={submitHandler}>
      <LabeledInput
        title="장소 입력"
        placeholder="ex. URL, 칼바람 나락"
        onChange={(e: ChangeEvent<HTMLInputElement>) => {
          setPlace(e.target.value);
        }}
        max={100}
        value={place}
      />
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
            disabled={place.length === 0}
            onClick={submitHandler}
            font={theme.typography.Medium}
          >
            확정하기
          </Button>
        </div>
      </div>
    </div>
  );
}
