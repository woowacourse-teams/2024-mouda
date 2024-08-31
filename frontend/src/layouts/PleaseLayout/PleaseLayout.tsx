import { PropsWithChildren } from 'react';
import PleaseMain from './PleaseMain/PleaseMain';
import PleaseFixedButtonWrapper from './PleaseFixedButtonWrapper/PleaseFixedButtonWrapper';
import * as S from './PleaseLayout.style';
import { useTheme } from '@emotion/react';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';

function PleaseLayout(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <div css={S.containerStyle({ theme })}>{children}</div>;
}

PleaseLayout.Header = StickyTriSectionHeader;
PleaseLayout.Main = PleaseMain;
PleaseLayout.HomeFixedButtonWrapper = PleaseFixedButtonWrapper;

export default PleaseLayout;
