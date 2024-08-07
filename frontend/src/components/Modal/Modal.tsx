import * as S from './Modal.style';

import { KeyboardEvent, PropsWithChildren, useEffect } from 'react';

import { useTheme } from '@emotion/react';

interface ModalProps extends PropsWithChildren {
  onClose: () => void;
  hasDarkDimmer?: boolean;
  position?: 'center' | 'bottom';
}

export default function Modal(props: ModalProps) {
  const {
    onClose,
    children,
    hasDarkDimmer = true,
    position = 'center',
  } = props;

  const theme = useTheme();
  useEffect(() => {
    if (!onClose) return;
    const handleModalKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape') {
        onClose();
      }
    };

    //@ts-expect-error:KeyboardEvent가 안먹음
    window.addEventListener('keydown', handleModalKeyDown);
  }, [onClose]);

  return (
    <div onClick={onClose} css={S.dimmer({ hasDarkDimmer })}>
      <div
        css={S.content({ position, theme })}
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </div>
    </div>
  );
}
