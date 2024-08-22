export interface OptionsPanelOption {
  description: string;
  onClick: () => void;
  hasTopBorder?: boolean;
}

interface OptionsPanelProps {
  options: OptionsPanelOption[];
  onClose: () => void;
  onAfterSelect: () => void;
  movedHeight?: string;
  movedWidth?: string;
  width?: string;
  maxHeight?: string;
}

import * as S from './OptionsPanel.style';

import { useTheme } from '@emotion/react';

export default function OptionsPanel(props: OptionsPanelProps) {
  const {
    options,
    onClose,
    onAfterSelect,
    width,
    movedHeight,
    movedWidth,
    maxHeight,
  } = props;
  const theme = useTheme();

  return (
    <>
      <div onClick={onClose} css={S.dimmer} />
      <div
        css={S.panel({
          width: width || '100px',
          movedHeight: movedHeight || '0px',
          movedWidth: movedWidth || '0px',
          maxHeight: maxHeight,
        })}
      >
        {options.map(({ description, onClick, hasTopBorder }) => {
          return (
            <div
              key={description}
              onClick={(e) => {
                e.stopPropagation();
                onClick();
                onAfterSelect();
              }}
              css={S.option({ theme, hasTopBorder })}
            >
              <span css={theme.typography.b1}>{description}</span>
            </div>
          );
        })}
      </div>
    </>
  );
}
