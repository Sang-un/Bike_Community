import PropTypes from 'prop-types';
// @mui
import { alpha, useTheme, styled } from '@mui/material/styles';
import { Box, Grid, Container, Typography, LinearProgress } from '@mui/material';
// hooks
import useResponsive from '../../hooks/useResponsive';
// utils
import { fPercent } from '../../utils/formatNumber';
// _mock_
import { _skills } from '../../_mock';
// components
import Image from '../../components/Image';
import { MotionInView, varFade } from '../../components/animate';

// ----------------------------------------------------------------------

const RootStyle = styled('div')(({ theme }) => ({
  textAlign: 'center',
  paddingTop: theme.spacing(20),
  paddingBottom: theme.spacing(10),
  [theme.breakpoints.up('md')]: {
    textAlign: 'left',
  },
}));

// ----------------------------------------------------------------------

export default function AboutWhat() {
  const theme = useTheme();

  const isDesktop = useResponsive('up', 'md');

  const isLight = theme.palette.mode === 'light';
  const shadow = `-40px 40px 80px ${alpha(isLight ? theme.palette.grey[500] : theme.palette.common.black, 0.48)}`;

  return (
    <RootStyle>
      <Container>
        <Grid container spacing={3}>
          {isDesktop && (
            <Grid item xs={12} md={6} lg={7} sx={{ pr: { md: 7 } }}>
              <Grid container spacing={3} alignItems="flex-end">
                <Grid item xs={6}>
                  <MotionInView variants={varFade().inUp}>
                    <Image
                      src="https://minimal-assets-api.vercel.app/assets/images/about/what-1.jpg"
                      ratio="3/4"
                      sx={{
                        borderRadius: 2,
                        boxShadow: shadow,
                      }}
                    />
                  </MotionInView>
                </Grid>
                <Grid item xs={6}>
                  <MotionInView variants={varFade().inUp}>
                    <Image
                      src="https://minimal-assets-api.vercel.app/assets/images/about/what-2.jpg"
                      ratio="1/1"
                      sx={{ borderRadius: 2 }}
                    />
                  </MotionInView>
                </Grid>
              </Grid>
            </Grid>
          )}

          <Grid item xs={12} md={6} lg={5}>
            <MotionInView variants={varFade().inRight}>
              <Typography variant="h2" sx={{ mb: 3 }}>
                What is RIDERTOWN?
              </Typography>
            </MotionInView>

            <MotionInView variants={varFade().inRight}>
              <Typography
                sx={{
                  color: (theme) => (theme.palette.mode === 'light' ? 'text.secondary' : 'common.white'),
                }}
              >
                저희는 성숙한 라이딩문화를 지향해요. <br/>항상 행복하고 편안한 라이딩이 되었으면 좋겠어요.
              </Typography>
            </MotionInView>

            <Box sx={{ my: 5 }}>
              {_skills.map((progress) => (
                <MotionInView key={progress.label} variants={varFade().inRight}>
                  <ProgressItem progress={progress} />
                </MotionInView>
              ))}
            </Box>
          </Grid>
        </Grid>
      </Container>
    </RootStyle>
  );
}

// ----------------------------------------------------------------------

ProgressItem.propTypes = {
  progress: PropTypes.shape({
    label: PropTypes.string,
    value: PropTypes.number,
  }),
};

function ProgressItem({ progress }) {
  const { label, value } = progress;

  return (
    <Box sx={{ mt: 3 }}>
      <Box sx={{ mb: 1.5, display: 'flex', alignItems: 'center' }}>
        <Typography variant="subtitle2">{label}&nbsp;-&nbsp;</Typography>
        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
          {fPercent(value)}
        </Typography>
      </Box>
      <LinearProgress
        variant="determinate"
        value={value}
        sx={{
          '& .MuiLinearProgress-bar': { bgcolor: 'grey.700' },
          '&.MuiLinearProgress-determinate': { bgcolor: 'divider' },
        }}
      />
    </Box>
  );
}
