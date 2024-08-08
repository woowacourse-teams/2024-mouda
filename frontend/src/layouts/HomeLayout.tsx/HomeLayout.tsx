import * as S from './HomeLayout.style';

import HomeFixedButtonWrapper from './HomeFixedButtonWrapper/HomeFixedButtonWrapper';
import HomeHeader from './HomeHeader/HomeHeader';
import HomeMain from './HomeMain/HomeMain';
import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

function HomeLayout(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <div css={S.containerStyle({ theme })}>{children}</div>;
}

HomeLayout.Header = HomeHeader;
HomeLayout.Main = HomeMain;
HomeLayout.HomeFixedButtonWrapper = HomeFixedButtonWrapper;

export default HomeLayout;
